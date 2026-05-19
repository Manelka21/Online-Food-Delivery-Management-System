package com.fooddelivery.repository;

import com.fooddelivery.model.Admin;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.*;
import java.util.*;

@Repository
public class AdminRepository {

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
                AdminRepository.class.getProtectionDomain().getCodeSource().getLocation().toURI()
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

    // ─── READ: Get all Admin records from users.txt ───
    public List<Admin> findAll() {
        List<Admin> admins = new ArrayList<>();
        Path path = DATA_DIR.resolve(FILE_NAME);
        if (!Files.exists(path)) return admins;

        try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] f = line.split(DELIMITER, -1);
                if (f.length >= 10 && f[1].equals("Admin")) {
                    Admin a = new Admin();
                    a.setPersonId(f[0]);
                    a.setName(f[2]);
                    a.setEmail(f[3]);
                    a.setPassword(f[4]);
                    a.setPhone(f[5]);
                    admins.add(a);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading admins: " + e.getMessage());
        }
        return admins;
    }

    // ─── READ: Find a single Admin by ID ───
    public Optional<Admin> findById(String id) {
        return findAll().stream()
                .filter(a -> a.getPersonId().equals(id))
                .findFirst();
    }

    // ─── READ: Find Admin by email and password (for login) ───
    public Optional<Admin> findByEmailAndPassword(String email, String password) {
        return findAll().stream()
                .filter(a -> a.getEmail().equals(email) && a.getPassword().equals(password))
                .findFirst();
    }

    // ─── READ: Check if an email already exists ───
    public boolean existsByEmail(String email) {
        return findAll().stream()
                .anyMatch(a -> a.getEmail().equals(email));
    }

    // ─── WRITE: Save an Admin (insert or update) ───
    public Admin save(Admin admin) {
        List<String[]> allRows = readAllRows();

        // Generate ID for new admins
        if (admin.getPersonId() == null || admin.getPersonId().isEmpty()) {
            admin.setPersonId(nextId(allRows));
        }

        // Remove old row if updating
        allRows.removeIf(r -> r.length > 0 && r[0].equals(admin.getPersonId()));

        // Add the new/updated row
        allRows.add(toRow(admin));

        writeAllRows(allRows);
        return admin;
    }

    // ─── DELETE: Remove an Admin by ID ───
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

    // Convert an Admin object to a pipe-delimited row
    private String[] toRow(Admin a) {
        return new String[]{
                a.getPersonId(), "Admin", a.getName(), a.getEmail(),
                a.getPassword(), a.getPhone(), "", "", "", "true"
        };
    }

    // Generate next ADM-XXX ID
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
        return String.format("ADM-%03d", max + 1);
    }
}
