package com.fooddelivery.repository;

import com.fooddelivery.model.MenuItem;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class MenuItemRepository {

    private static final String FILE = "menu_items.txt";

    private MenuItem parse(String[] f) {
        if (f.length < 7) return null;
        return new MenuItem(f[0], f[1], f[2], f[3],
            Double.parseDouble(f[4]), f[5], Boolean.parseBoolean(f[6]));
    }

    private String[] toFields(MenuItem m) {
        return new String[]{
            m.getItemId(), m.getRestaurantId(), m.getName(), m.getDescription(),
            String.valueOf(m.getPrice()), m.getCategory(), String.valueOf(m.isAvailable())
        };
    }

    public List<MenuItem> findAll() {
        List<MenuItem> list = new ArrayList<>();
        for (String[] f : FileUtil.readAll(FILE)) {
            MenuItem m = parse(f);
            if (m != null) list.add(m);
        }
        return list;
    }

    public Optional<MenuItem> findById(String id) {
        for (String[] f : FileUtil.readAll(FILE))
            if (f[0].equals(id)) return Optional.ofNullable(parse(f));
        return Optional.empty();
    }

    public List<MenuItem> findByRestaurantId(String restaurantId) {
        List<MenuItem> list = new ArrayList<>();
        for (String[] f : FileUtil.readAll(FILE))
            if (f.length >= 2 && f[1].equals(restaurantId))
                list.add(parse(f));
        return list;
    }

    public MenuItem save(MenuItem m) {
        List<String[]> records = FileUtil.readAll(FILE);
        boolean found = false;
        for (int i = 0; i < records.size(); i++) {
            if (records.get(i)[0].equals(m.getItemId())) {
                records.set(i, toFields(m)); found = true; break;
            }
        }
        if (!found) {
            if (m.getItemId() == null || m.getItemId().isEmpty())
                m.setItemId(FileUtil.nextId(FILE, "ITM", 0));
            records.add(toFields(m));
        }
        FileUtil.writeAll(FILE, records);
        return m;
    }

    public void deleteById(String id) {
        List<String[]> records = FileUtil.readAll(FILE);
        records.removeIf(f -> f[0].equals(id));
        FileUtil.writeAll(FILE, records);
    }
}
