package com.fooddelivery.repository;

import com.fooddelivery.model.Order;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class OrderRepository {

    private static final String FILE = "orders.txt";

    private Order parse(String[] f) {
        if (f.length < 8) return null;
        Order o = new Order();
        o.setOrderId(f[0]);
        o.setBatchId(f[1]);
        o.setCustomerId(f[2]);
        o.setRestaurantId(f[3]);
        o.setStatus(f[4]);
        o.setOrderDate(f[5]);
        o.setTotalAmount(Double.parseDouble(f[6]));
        o.setDeliveryAddress(f[7]);
        return o;
    }

    private String[] toFields(Order o) {
        return new String[]{
            o.getOrderId(), o.getBatchId(), o.getCustomerId(), o.getRestaurantId(),
            o.getStatus(), o.getOrderDate(),
            String.valueOf(o.getTotalAmount()), o.getDeliveryAddress()
        };
    }

    public List<Order> findAll() {
        List<Order> list = new ArrayList<>();
        for (String[] f : FileUtil.readAll(FILE)) {
            Order o = parse(f);
            if (o != null) list.add(o);
        }
        return list;
    }

    public Optional<Order> findById(String id) {
        for (String[] f : FileUtil.readAll(FILE))
            if (f[0].equals(id)) return Optional.ofNullable(parse(f));
        return Optional.empty();
    }

    public List<Order> findByCustomerId(String customerId) {
        List<Order> list = new ArrayList<>();
        for (String[] f : FileUtil.readAll(FILE))
            if (f.length >= 3 && f[2].equals(customerId))
                list.add(parse(f));
        return list;
    }

    public List<Order> findByBatchId(String batchId) {
        List<Order> list = new ArrayList<>();
        for (String[] f : FileUtil.readAll(FILE))
            if (f.length >= 2 && f[1].equals(batchId))
                list.add(parse(f));
        return list;
    }

    public Order save(Order o) {
        List<String[]> records = FileUtil.readAll(FILE);
        boolean found = false;
        for (int i = 0; i < records.size(); i++) {
            if (records.get(i)[0].equals(o.getOrderId())) {
                records.set(i, toFields(o)); found = true; break;
            }
        }
        if (!found) {
            if (o.getOrderId() == null || o.getOrderId().isEmpty())
                o.setOrderId(FileUtil.nextId(FILE, "ORD", 0));
            records.add(toFields(o));
        }
        FileUtil.writeAll(FILE, records);
        return o;
    }

    public void deleteById(String id) {
        List<String[]> records = FileUtil.readAll(FILE);
        records.removeIf(f -> f[0].equals(id));
        FileUtil.writeAll(FILE, records);
    }
}
