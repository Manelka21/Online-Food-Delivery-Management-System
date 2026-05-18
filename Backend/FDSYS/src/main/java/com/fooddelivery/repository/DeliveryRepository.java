package com.fooddelivery.repository;

import com.fooddelivery.model.Delivery;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class DeliveryRepository {

    private static final String FILE = "deliveries.txt";

    private Delivery parse(String[] f) {
        if (f.length < 6) return null;
        Delivery d = new Delivery();
        d.setDeliveryId(f[0]);
        d.setOrderId(f[1]);
        d.setDriverId(f[2]);
        d.setAssignedTime(f[3]);
        d.setDeliveredTime(f[4].isEmpty() ? null : f[4]);
        d.setStatus(f[5]);
        return d;
    }

    private String[] toFields(Delivery d) {
        return new String[]{
            d.getDeliveryId(), d.getOrderId(), d.getDriverId(),
            d.getAssignedTime() != null ? d.getAssignedTime() : "",
            d.getDeliveredTime() != null ? d.getDeliveredTime() : "",
            d.getStatus()
        };
    }

    public List<Delivery> findAll() {
        List<Delivery> list = new ArrayList<>();
        for (String[] f : FileUtil.readAll(FILE)) {
            Delivery d = parse(f);
            if (d != null) list.add(d);
        }
        return list;
    }

    public Optional<Delivery> findById(String id) {
        for (String[] f : FileUtil.readAll(FILE))
            if (f[0].equals(id)) return Optional.ofNullable(parse(f));
        return Optional.empty();
    }

    public Optional<Delivery> findByOrderId(String orderId) {
        for (String[] f : FileUtil.readAll(FILE))
            if (f.length >= 2 && f[1].equals(orderId))
                return Optional.ofNullable(parse(f));
        return Optional.empty();
    }

    public Delivery save(Delivery d) {
        List<String[]> records = FileUtil.readAll(FILE);
        boolean found = false;
        for (int i = 0; i < records.size(); i++) {
            if (records.get(i)[0].equals(d.getDeliveryId())) {
                records.set(i, toFields(d)); found = true; break;
            }
        }
        if (!found) {
            if (d.getDeliveryId() == null || d.getDeliveryId().isEmpty())
                d.setDeliveryId(FileUtil.nextId(FILE, "DEL", 0));
            records.add(toFields(d));
        }
        FileUtil.writeAll(FILE, records);
        return d;
    }
}
