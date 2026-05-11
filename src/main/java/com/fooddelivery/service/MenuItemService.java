package com.fooddelivery.service;

// MenuItemService - handles all file based CRUD operations

import com.fooddelivery.model.MenuItem;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class MenuItemService {

    private static final String DATA_FILE = "data/menuItems.txt";

    private static MenuItemService instance;

    private MenuItemService() { ensureFileExists(); }

    public static synchronized MenuItemService getInstance() {
        if (instance == null) instance = new MenuItemService();
        return instance;
    }

    private void ensureFileExists() {
        try {
            Path path = Paths.get(DATA_FILE);
            if (!Files.exists(path)) {
                Files.createDirectories(path.getParent());
                Files.createFile(path);
            }
        } catch (IOException e) {
            throw new RuntimeException("Cannot initialise data file: " + DATA_FILE, e);
        }
    }

    private List<MenuItem> readAll() throws IOException {
        List<MenuItem> items = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    MenuItem item = MenuItem.fromFileString(line.trim());
                    if (item != null) items.add(item);
                }
            }
        }
        return items;
    }

    private void writeAll(List<MenuItem> items) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(DATA_FILE, false))) {
            for (MenuItem item : items) {
                bw.write(item.toFileString());
                bw.newLine();
            }
        }
    }

    private String generateId() {
        return "MI" + System.currentTimeMillis();
    }

    // CREATE
    public MenuItem create(MenuItem item) throws IOException {
        item.setId(generateId());
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(DATA_FILE, true))) {
            bw.write(item.toFileString());
            bw.newLine();
        }
        return item;
    }

    // READ
    public List<MenuItem> getAll() throws IOException {
        return readAll();
    }

    public MenuItem getById(String id) throws IOException {
        return readAll().stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<MenuItem> search(String query) throws IOException {
        String q = query.toLowerCase();
        return readAll().stream()
                .filter(item -> item.getName().toLowerCase().contains(q)
                        || item.getCategory().toLowerCase().contains(q)
                        || item.getDescription().toLowerCase().contains(q))
                .collect(Collectors.toList());
    }

    public List<MenuItem> getByCategory(String category) throws IOException {
        return readAll().stream()
                .filter(item -> item.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    // UPDATE
    public boolean update(MenuItem updated) throws IOException {
        List<MenuItem> items = readAll();
        boolean found = false;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(updated.getId())) {
                items.set(i, updated);
                found = true;
                break;
            }
        }
        if (found) writeAll(items);
        return found;
    }

    // DELETE
    public boolean delete(String id) throws IOException {
        List<MenuItem> items = readAll();
        boolean removed = items.removeIf(item -> item.getId().equals(id));
        if (removed) writeAll(items);
        return removed;
    }

    public List<String> getAllCategories() throws IOException {
        return readAll().stream()
                .map(MenuItem::getCategory)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }
}
