package com.fooddelivery.repository;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FileUtil {

    private static final Path DATA_DIR = findProjectDataDir();
    private static final String DELIMITER = "\\|";
    private static final String WRITE_DELIMITER = "|";

    private static Path findProjectDataDir() {
        try {
            Path classLocation = Paths.get(
                FileUtil.class.getProtectionDomain().getCodeSource().getLocation().toURI()
            );
            // classLocation is typically: <project>/target/classes
            Path projectRoot = classLocation.getParent().getParent();
            Path dataDir = projectRoot.resolve("data");
            if (Files.exists(dataDir)) return dataDir;
        } catch (Exception ignored) {}

        Path cwdData = Paths.get("data");
        if (Files.exists(cwdData)) return cwdData.toAbsolutePath();

        try { Files.createDirectories(cwdData); } catch (IOException ignored) {}
        return cwdData.toAbsolutePath();
    }

    public static List<String[]> readAll(String filename) {
        List<String[]> records = new ArrayList<>();
        Path path = DATA_DIR.resolve(filename);
        if (!Files.exists(path)) return records;
        try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    records.add(line.split(DELIMITER, -1));
                }
            }
        } catch (IOException e) {
            System.err.println("FileUtil ERROR reading " + filename + ": " + e.getMessage());
        }
        return records;
    }

    public static void writeAll(String filename, List<String[]> records) {
        Path path = DATA_DIR.resolve(filename);
        try {
            Files.createDirectories(path.getParent());
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile()))) {
                for (String[] record : records) {
                    writer.write(String.join(WRITE_DELIMITER, record));
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("FileUtil ERROR writing " + filename + ": " + e.getMessage());
        }
    }

    public static void appendOne(String filename, String[] fields) {
        Path path = DATA_DIR.resolve(filename);
        try {
            Files.createDirectories(path.getParent());
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile(), true))) {
                writer.write(String.join(WRITE_DELIMITER, fields));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("FileUtil ERROR appending to " + filename + ": " + e.getMessage());
        }
    }

    public static String nextId(String filename, String prefix, int idFieldIndex) {
        List<String[]> records = readAll(filename);
        int max = 0;
        for (String[] r : records) {
            if (r.length > idFieldIndex) {
                try {
                    String numPart = r[idFieldIndex].replaceAll("[^0-9]", "");
                    int num = Integer.parseInt(numPart);
                    if (num > max) max = num;
                } catch (NumberFormatException ignored) {}
            }
        }
        return String.format("%s-%03d", prefix, max + 1);
    }
}
