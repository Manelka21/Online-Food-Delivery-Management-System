package com.Fooddelivery.Food_delivery.Service;

import com.Fooddelivery.Food_delivery.model.CartItem;
import java.util.List;

public abstract class FileService {

    public abstract List<CartItem> getAllItems();
    public abstract void addItem(CartItem item);
    public abstract void updateQuantity(String name, int quantity);
    public abstract void removeItem(String name);
    public abstract void clearCart();

    public double calculateTotal(List<CartItem> items) {
        double total = 0;
        for (CartItem item : items) {
            total += item.getTotal();
        }
        return total;
    }
}
