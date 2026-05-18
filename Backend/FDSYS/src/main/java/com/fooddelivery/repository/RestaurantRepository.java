package com.fooddelivery.repository;

import com.fooddelivery.model.Restaurant;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class RestaurantRepository {

    private static final String FILE = "restaurants.txt";

    private Restaurant parse(String[] f) {
        if (f.length < 6) return null;
        return new Restaurant(f[0], f[1], f[2], f[3], f[4], f[5]);
    }

    private String[] toFields(Restaurant r) {
        return new String[]{
            r.getRestaurantId(), r.getName(), r.getAddress(),
            r.getPhone(), r.getCuisineType(), r.getStatus()
        };
    }

    public List<Restaurant> findAll() {
        List<Restaurant> list = new ArrayList<>();
        for (String[] f : FileUtil.readAll(FILE)) {
            Restaurant r = parse(f);
            if (r != null) list.add(r);
        }
        return list;
    }

    public Optional<Restaurant> findById(String id) {
        for (String[] f : FileUtil.readAll(FILE))
            if (f[0].equals(id)) return Optional.ofNullable(parse(f));
        return Optional.empty();
    }

    public List<Restaurant> findByStatus(String status) {
        List<Restaurant> list = new ArrayList<>();
        for (String[] f : FileUtil.readAll(FILE))
            if (f.length >= 6 && f[5].equalsIgnoreCase(status))
                list.add(parse(f));
        return list;
    }

    public Restaurant save(Restaurant r) {
        List<String[]> records = FileUtil.readAll(FILE);
        boolean found = false;
        for (int i = 0; i < records.size(); i++) {
            if (records.get(i)[0].equals(r.getRestaurantId())) {
                records.set(i, toFields(r)); found = true; break;
            }
        }
        if (!found) {
            if (r.getRestaurantId() == null || r.getRestaurantId().isEmpty())
                r.setRestaurantId(FileUtil.nextId(FILE, "RES", 0));
            records.add(toFields(r));
        }
        FileUtil.writeAll(FILE, records);
        return r;
    }

    public void deleteById(String id) {
        List<String[]> records = FileUtil.readAll(FILE);
        records.removeIf(f -> f[0].equals(id));
        FileUtil.writeAll(FILE, records);
    }
}
