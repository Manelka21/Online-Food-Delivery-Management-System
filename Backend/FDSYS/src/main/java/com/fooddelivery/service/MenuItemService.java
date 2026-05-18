package com.fooddelivery.service;

import com.fooddelivery.model.MenuItem;
import com.fooddelivery.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class MenuItemService {

    @Autowired
    private MenuItemRepository repo;

    public List<MenuItem> getAll()                           { return repo.findAll(); }
    public Optional<MenuItem> getById(String id)             { return repo.findById(id); }
    public List<MenuItem> getByRestaurant(String rid)        { return repo.findByRestaurantId(rid); }
    public MenuItem save(MenuItem m)                         { return repo.save(m); }
    public void delete(String id)                            { repo.deleteById(id); }

    public MenuItem update(String id, MenuItem updated) {
        MenuItem existing = repo.findById(id)
            .orElseThrow(() -> new RuntimeException("Menu item not found"));
        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());
        existing.setPrice(updated.getPrice());
        existing.setCategory(updated.getCategory());
        existing.setAvailable(updated.isAvailable());
        return repo.save(existing);
    }
}
