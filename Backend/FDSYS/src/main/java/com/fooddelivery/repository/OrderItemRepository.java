package com.fooddelivery.repository;

import com.fooddelivery.model.OrderItem;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class OrderItemRepository {

    private static final String FILE = "order_items.txt";

    private OrderItem parse(String[] f) {
        if (f.length < 5) return null;
        OrderItem oi = new OrderItem();
        oi.setOrderItemId(f[0]);
        oi.setOrderId(f[1]);
        oi.setItemId(f[2]);
        oi.setQuantity(Integer.parseInt(f[3]));
        oi.setUnitPrice(Double.parseDouble(f[4]));
        return oi;
    }

    private String[] toFields(OrderItem oi) {
        return new String[]{
            oi.getOrderItemId(), oi.getOrderId(), oi.getItemId(),
            String.valueOf(oi.getQuantity()), String.valueOf(oi.getUnitPrice())
        };
    }

    public List<OrderItem> findAll() {
        List<OrderItem> list = new ArrayList<>();
        for (String[] f : FileUtil.readAll(FILE)) {
            OrderItem oi = parse(f);
            if (oi != null) list.add(oi);
        }
        return list;
    }

    public List<OrderItem> findByOrderId(String orderId) {
        List<OrderItem> list = new ArrayList<>();
        for (String[] f : FileUtil.readAll(FILE))
            if (f.length >= 2 && f[1].equals(orderId))
                list.add(parse(f));
        return list;
    }

    public OrderItem save(OrderItem oi) {
        List<String[]> records = FileUtil.readAll(FILE);
        boolean found = false;
        for (int i = 0; i < records.size(); i++) {
            if (records.get(i)[0].equals(oi.getOrderItemId())) {
                records.set(i, toFields(oi)); found = true; break;
            }
        }
        if (!found) {
            if (oi.getOrderItemId() == null || oi.getOrderItemId().isEmpty())
                oi.setOrderItemId(FileUtil.nextId(FILE, "OI", 0));
            records.add(toFields(oi));
        }
        FileUtil.writeAll(FILE, records);
        return oi;
    }
}
