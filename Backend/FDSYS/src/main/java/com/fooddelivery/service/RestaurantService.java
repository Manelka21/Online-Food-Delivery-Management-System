package com.fooddelivery.service;

import com.fooddelivery.model.Restaurant;
import com.fooddelivery.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository repo;

    public List<Restaurant> getAll()                    { return repo.findAll(); }
    public Optional<Restaurant> getById(String id)      { return repo.findById(id); }
    public List<Restaurant> getOpenRestaurants()        { return repo.findByStatus("Open"); }
    public Restaurant save(Restaurant r)                { return repo.save(r); }
    public void delete(String id)                       { repo.deleteById(id); }

    public Restaurant update(String id, Restaurant updated) {
        Restaurant existing = repo.findById(id)
            .orElseThrow(() -> new RuntimeException("Restaurant not found"));
        existing.setName(updated.getName());
        existing.setAddress(updated.getAddress());
        existing.setPhone(updated.getPhone());
        existing.setCuisineType(updated.getCuisineType());
        existing.setStatus(updated.getStatus());
        return repo.save(existing);
    }
}
