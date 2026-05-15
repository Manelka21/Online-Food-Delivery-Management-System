package com.fooddelivery.repository;

import com.fooddelivery.model.Customer;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.*;
import java.util.*;

@Repository
public class CustomerRepository {

    // Resolve data path relative to the project root (where pom.xml lives),
    // NOT the JVM working directory — this prevents CWD mismatch issues.
    private static final Path DATA_DIR = findProjectDataDir();
    private static final String FILE_NAME = "users.txt";
    private static final String DELIMITER = "\\|";
    private static final String SEPARATOR = "|";

    private static Path findProjectDataDir() {
        // 1) Try locating the project root by walking up from the compiled class location
        try {
            Path classLocation = Paths.get(
                CustomerRepository.class.getProtectionDomain().getCodeSource().getLocation().toURI()
            );
            // classLocation is typically: <project>/target/classes
            Path projectRoot = classLocation.getParent().getParent(); // go up from target/classes
            Path dataDir = projectRoot.resolve("data");
            if (Files.exists(dataDir)) {
                return dataDir;
            }
        } catch (Exception ignored) {}

        // 2) Fallback: try the CWD-relative path (works when CWD == project root)
        Path cwdData = Paths.get("data");
        if (Files.exists(cwdData)) {
            return cwdData.toAbsolutePath();
        }

        // 3) Last resort: create data dir at CWD
        try { Files.createDirectories(cwdData); } catch (IOException ignored) {}
        return cwdData.toAbsolutePath();
    }

    // ─── READ: Get all Customer records from users.txt ───
    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();
        Path path = DATA_DIR.resolve(FILE_NAME);
        if (!Files.exists(path)) return customers;

        try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] f = line.split(DELIMITER, -1);
                if (f.length >= 10 && f[1].equals("Customer")) {
                    Customer c = new Customer();
                    c.setPersonId(f[0]);
                    c.setName(f[2]);
                    c.setEmail(f[3]);
                    c.setPassword(f[4]);
                    c.setPhone(f[5]);
                    c.setAddress(f[6]);
                    customers.add(c);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading customers: " + e.getMessage());
        }
        return customers;
    }

    // ─── READ: Find a single Customer by ID ───
    public Optional<Customer> findById(String id) {
        return findAll().stream()
                .filter(c -> c.getPersonId().equals(id))
                .findFirst();
    }

    // ─── READ: Find Customer by email and password (for login) ───
    public Optional<Customer> findByEmailAndPassword(String email, String password) {
        return findAll().stream()
                .filter(c -> c.getEmail().equals(email) && c.getPassword().equals(password))
                .findFirst();
    }

    // ─── READ: Check if an email already exists ───
    public boolean existsByEmail(String email) {
        return findAll().stream()
                .anyMatch(c -> c.getEmail().equals(email));
    }

    // ─── WRITE: Save a Customer (insert or update) ───
    public Customer save(Customer customer) {
        List<String[]> allRows = readAllRows();

        // Generate ID for new customers
        if (customer.getPersonId() == null || customer.getPersonId().isEmpty()) {
            customer.setPersonId(nextId(allRows));
        }

        // Remove old row if updating
        allRows.removeIf(r -> r.length > 0 && r[0].equals(customer.getPersonId()));

        // Add the new/updated row
        allRows.add(toRow(customer));

        writeAllRows(allRows);
        return customer;
    }

    // ─── DELETE: Remove a Customer by ID ───
    public void deleteById(String id) {
        List<String[]> allRows = readAllRows();
        allRows.removeIf(r -> r.length > 0 && r[0].equals(id));
        writeAllRows(allRows);
    }

    // ══════════════════════════════════════════
    //  Private helper methods (simple file I/O)
    // ══════════════════════════════════════════

    // Read ALL rows from users.txt (all roles: Customer, Admin, Driver)
    private List<String[]> readAllRows() {
        List<String[]> rows = new ArrayList<>();
        Path path = DATA_DIR.resolve(FILE_NAME);
        if (!Files.exists(path)) return rows;

        try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    rows.add(line.split(DELIMITER, -1));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return rows;
    }

    // Write ALL rows back to users.txt
    private void writeAllRows(List<String[]> rows) {
        try {
            Files.createDirectories(DATA_DIR);
            Path path = DATA_DIR.resolve(FILE_NAME);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile()))) {
                for (String[] row : rows) {
                    writer.write(String.join(SEPARATOR, row));
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }

    // Convert a Customer object to a pipe-delimited row
    private String[] toRow(Customer c) {
        return new String[]{
                c.getPersonId(),
                "Customer",
                c.getName(),
                c.getEmail(),
                c.getPassword(),
                c.getPhone(),
                c.getAddress() != null ? c.getAddress() : "",
                "", "", "true"
        };
    }

    // Generate next USR-XXX ID
    private String nextId(List<String[]> allRows) {
        int max = 0;
        for (String[] r : allRows) {
            if (r.length > 0) {
                try {
                    String numPart = r[0].replaceAll("[^0-9]", "");
                    int num = Integer.parseInt(numPart);
                    if (num > max) max = num;
                } catch (NumberFormatException ignored) {}
            }
        }
        return String.format("USR-%03d", max + 1);
    }
}