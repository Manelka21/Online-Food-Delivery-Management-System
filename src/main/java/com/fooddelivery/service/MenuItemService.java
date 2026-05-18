package com.fooddelivery.service;

// MenuItemService - handles all file based CRUD operations

import com.fooddelivery.model.MenuItem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

// This class handles all CRUD operations for menu items
// It reads and writes data to menuItems.txt file
// OOP Concept: Abstraction - file handling details are hidden inside private methods
public class MenuItemService {

    // Path to the data file
    private String dataFile = "data/menuItems.txt";

    // Constructor - creates the data file if it does not exist
    public MenuItemService() {
        createFileIfNotExists();
    }

    // This private method creates the file if it does not exist
    // Private means it is hidden from outside - this is abstraction
    private void createFileIfNotExists() {
        try {
            if (!Files.exists(Paths.get(dataFile))) {
                Files.createDirectories(Paths.get("data"));
                Files.createFile(Paths.get(dataFile));
            }
        } catch (IOException e) {
            System.out.println("Error creating file: " + e.getMessage());
        }
    }

    // This private method reads all items from the file
    // Returns a list of MenuItem objects
    // Private - hidden from outside, only used inside this class
    private List<MenuItem> readAll() throws IOException {
        List<MenuItem> items = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(dataFile));
        String line;

        // Read one line at a time until end of file
        while ((line = reader.readLine()) != null) {
            // Skip empty lines
            if (!line.trim().isEmpty()) {
                // Convert text line to MenuItem object
                MenuItem item = MenuItem.fromFileString(line.trim());
                if (item != null) {
                    items.add(item);
                }
            }
        }
        reader.close();
        return items;
    }

    // This private method writes all items back to the file
    // It overwrites the existing file with new data
    private void writeAll(List<MenuItem> items) throws IOException {
        // false means overwrite the file, not append
        BufferedWriter writer = new BufferedWriter(new FileWriter(dataFile, false));

        for (MenuItem item : items) {
            // Convert MenuItem object to text line and write it
            writer.write(item.toFileString());
            writer.newLine(); // go to next line
        }
        writer.close();
    }

    // This method generates a simple unique ID for each item
    private String generateId() {
        return "MI" + System.currentTimeMillis();
    }

    // ==================== CREATE ====================
    // This method adds a new menu item to the file
    public MenuItem create(MenuItem item) throws IOException {
        // Generate a unique ID for the new item
        item.setId(generateId());

        // Open file in append mode (true = append, not overwrite)
        BufferedWriter writer = new BufferedWriter(new FileWriter(dataFile, true));

        // Write the new item as a new line at the end of the file
        writer.write(item.toFileString());
        writer.newLine();
        writer.close();

        return item;
    }

    // ==================== READ ====================
    // This method returns all menu items from the file
    public List<MenuItem> getAll() throws IOException {
        return readAll();
    }

    // This method finds one item by its ID
    public MenuItem getById(String id) throws IOException {
        List<MenuItem> items = readAll();

        // Loop through all items to find the matching ID
        for (MenuItem item : items) {
            if (item.getId().equals(id)) {
                return item; // found it!
            }
        }
        return null; // not found
    }

    // Case insensitive search by name, category or description
    public List<MenuItem> search(String query) throws IOException {
        List<MenuItem> allItems = readAll();
        List<MenuItem> results = new ArrayList<>();
        String lowerQuery = query.toLowerCase();

        for (MenuItem item : allItems) {
            // Check if name, category or description contains the search word
            if (item.getName().toLowerCase().contains(lowerQuery) ||
                    item.getCategory().toLowerCase().contains(lowerQuery) ||
                    item.getDescription().toLowerCase().contains(lowerQuery)) {
                results.add(item);
            }
        }
        return results;
    }

    // This method returns items filtered by category
    public List<MenuItem> getByCategory(String category) throws IOException {
        List<MenuItem> allItems = readAll();
        List<MenuItem> results = new ArrayList<>();

        for (MenuItem item : allItems) {
            if (item.getCategory().equalsIgnoreCase(category)) {
                results.add(item);
            }
        }
        return results;
    }

    // ==================== UPDATE ====================
    // This method updates an existing menu item in the file
    public boolean update(MenuItem updatedItem) throws IOException {
        List<MenuItem> items = readAll();
        boolean found = false;

        // Loop through all items to find the one to update
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(updatedItem.getId())) {
                // Replace old item with updated item
                items.set(i, updatedItem);
                found = true;
                break;
            }
        }

        // If item was found, rewrite the whole file with updated data
        if (found) {
            writeAll(items);
        }

        return found;
    }

    // ==================== DELETE ====================
    // This method removes a menu item from the file by ID
    public boolean delete(String id) throws IOException {
        List<MenuItem> items = readAll();
        boolean removed = false;

        // Loop through items to find and remove the matching one
        List<MenuItem> updatedItems = new ArrayList<>();
        for (MenuItem item : items) {
            if (item.getId().equals(id)) {
                removed = true; // skip this item (delete it)
            } else {
                updatedItems.add(item); // keep this item
            }
        }

        // Rewrite the file without the deleted item
        if (removed) {
            writeAll(updatedItems);
        }

        return removed;
    }

    // This method returns a list of all unique categories
    public List<String> getAllCategories() throws IOException {
        List<MenuItem> items = readAll();
        List<String> categories = new ArrayList<>();

        for (MenuItem item : items) {
            // Only add category if not already in the list
            if (!categories.contains(item.getCategory())) {
                categories.add(item.getCategory());
            }
        }
        return categories;
    }
}
