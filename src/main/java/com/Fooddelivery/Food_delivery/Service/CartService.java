package com.Fooddelivery.Food_delivery.Service;

import com.Fooddelivery.Food_delivery.model.CartItem;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartService extends FileService {

    private static final String FILE_PATH = "cart.txt";

    @Override
    public List<CartItem> getAllItems() {
        List<CartItem> items = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) return items;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    items.add(CartItem.fromFileString(line));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public void addItem(CartItem newItem) {
        List<CartItem> items = getAllItems();
        boolean found = false;

        for (CartItem item : items) {
            if (item.getName().equals(newItem.getName())) {
                item.setQuantity(item.getQuantity() + newItem.getQuantity());
                found = true;
                break;
            }
        }

        if (!found) {
            items.add(newItem);
        }
        saveAllItems(items);
    }

    @Override
    public void updateQuantity(String name, int quantity) {
        List<CartItem> items = getAllItems();
        for (CartItem item : items) {
            if (item.getName().equals(name)) {
                item.setQuantity(quantity);
                break;
            }
        }
        saveAllItems(items);
    }

    @Override
    public void removeItem(String name) {
        List<CartItem> items = getAllItems();
        items.removeIf(item -> item.getName().equals(name));
        saveAllItems(items);
    }

    @Override
    public void clearCart() {
        saveAllItems(new ArrayList<>());
    }

    public double getGrandTotal() {
        return calculateTotal(getAllItems());
    }

    private void saveAllItems(List<CartItem> items) {
        try{
            File file=new File(FILE_PATH);
            System.out.println("Saving to:"+file.getAbsolutePath());

                BufferedWriter writer = new BufferedWriter(new FileWriter(file)) ;
            for (CartItem item : items) {
                writer.write(item.toFileString());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }

