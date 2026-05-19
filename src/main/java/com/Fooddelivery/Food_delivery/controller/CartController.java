package com.Fooddelivery.Food_delivery.controller;

import com.Fooddelivery.Food_delivery.model.CartItem;
import com.Fooddelivery.Food_delivery.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @GetMapping
    public String viewCart(Model model) {
        model.addAttribute("cartItems", cartService.getAllItems());
        model.addAttribute("grandTotal", cartService.getGrandTotal());
        return "cart";
    }

    @PostMapping("/add")
    public String addItem(@RequestParam String name,
                          @RequestParam double price,
                          @RequestParam int quantity,
                          @RequestParam String category) {
        cartService.addItem(new CartItem(name, price, quantity,category ));
        return "redirect:/cart";
    }

    @PostMapping("/update")
    public String updateItem(@RequestParam String name,
                             @RequestParam int quantity) {
        cartService.updateQuantity(name, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String removeItem(@RequestParam String name) {
        cartService.removeItem(name);
        return "redirect:/cart";
    }

    @PostMapping("/clear")
    public String clearCart() {
        cartService.clearCart();
        return "redirect:/cart";
    }
}
