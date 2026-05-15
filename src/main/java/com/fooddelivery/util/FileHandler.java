package com.fooddelivery.util;

import com.fooddelivery.model.DeliveryDriver;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles all file read/write operations for DeliveryDriver data.
 * Uses pipe-delimited text file (drivers.txt) for persistence.
 */
@Component
public class FileHandler {

    // Path to the data file — created relative to the working directory (project root)
    private static final String DATA_DIR  = "data";
    private static final String FILE_NAME = "drivers.txt";
    private static final String FILE_PATH = DATA_DIR + File.separator + FILE_NAME;

    public FileHandler() {
        ensureFileExists();
    }

    /** Create data directory and file if they don't exist. */
    private void ensureFileExists() {
        try {
            Files.createDirectories(Paths.get(DATA_DIR));
            Path filePath = Paths.get(FILE_PATH);
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }
        } catch (IOException e) {
            System.err.println("[FileHandler] Could not initialise data file: " + e.getMessage());
        }
    }

    // ------------------------------------------------------------------ READ
    /** Read all drivers from file. */
    public List<DeliveryDriver> readAll() {
        List<DeliveryDriver> drivers = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    DeliveryDriver d = DeliveryDriver.fromFileString(line);
                    if (d != null) drivers.add(d);
                }
            }
        } catch (IOException e) {
            System.err.println("[FileHandler] Error reading file: " + e.getMessage());
        }
        return drivers;
    }

    /** Find a driver by ID. Returns null if not found. */
    public DeliveryDriver findById(String id) {
        return readAll().stream()
                .filter(d -> d.getDriverId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /** Find a driver by email. Returns null if not found. */
    public DeliveryDriver findByEmail(String email) {
        return readAll().stream()
                .filter(d -> d.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    // ----------------------------------------------------------------- WRITE
    /** Overwrite the entire file with the given list. */
    private void writeAll(List<DeliveryDriver> drivers) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
            for (DeliveryDriver d : drivers) {
                bw.write(d.toFileString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("[FileHandler] Error writing file: " + e.getMessage());
        }
    }

    // --------------------------------------------------------------- CREATE
    /** Append a new driver. Returns false if email already exists. */
    public boolean create(DeliveryDriver driver) {
        if (findByEmail(driver.getEmail()) != null) return false; // duplicate email
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            bw.write(driver.toFileString());
            bw.newLine();
            return true;
        } catch (IOException e) {
            System.err.println("[FileHandler] Error creating driver: " + e.getMessage());
            return false;
        }
    }

    // --------------------------------------------------------------- UPDATE
    /** Update an existing driver by ID. Returns false if not found. */
    public boolean update(DeliveryDriver updated) {
        List<DeliveryDriver> drivers = readAll();
        boolean found = false;
        for (int i = 0; i < drivers.size(); i++) {
            if (drivers.get(i).getDriverId().equals(updated.getDriverId())) {
                // Preserve original password if not changed
                if (updated.getPassword() == null || updated.getPassword().isBlank()) {
                    updated.setPassword(drivers.get(i).getPassword());
                }
                drivers.set(i, updated);
                found = true;
                break;
            }
        }
        if (found) writeAll(drivers);
        return found;
    }

    // --------------------------------------------------------------- DELETE
    /** Delete a driver by ID. Returns false if not found. */
    public boolean delete(String id) {
        List<DeliveryDriver> drivers = readAll();
        boolean removed = drivers.removeIf(d -> d.getDriverId().equals(id));
        if (removed) writeAll(drivers);
        return removed;
    }

    // ----------------------------------------------------------- ID GENERATOR
    /** Generate a new unique driver ID (DD001, DD002, …). */
    public String generateId() {
        List<DeliveryDriver> drivers = readAll();
        int max = 0;
        for (DeliveryDriver d : drivers) {
            try {
                int num = Integer.parseInt(d.getDriverId().replace("DD", ""));
                if (num > max) max = num;
            } catch (NumberFormatException ignored) {}
        }
        return String.format("DD%03d", max + 1);
    }
}
