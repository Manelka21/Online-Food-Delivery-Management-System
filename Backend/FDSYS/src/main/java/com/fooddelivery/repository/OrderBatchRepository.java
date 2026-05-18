package com.fooddelivery.repository;

import com.fooddelivery.model.OrderBatch;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class OrderBatchRepository {

    private static final String FILE = "order_batches.txt";

    private OrderBatch parse(String[] f) {
        if (f.length < 5) return null;
        return new OrderBatch(f[0], f[1], f[2],
            Double.parseDouble(f[3]), Integer.parseInt(f[4]));
    }

    private String[] toFields(OrderBatch b) {
        return new String[]{
            b.getBatchId(), b.getCustomerId(), b.getBatchDate(),
            String.valueOf(b.getTotalAmount()), String.valueOf(b.getOrderCount())
        };
    }

    public List<OrderBatch> findAll() {
        List<OrderBatch> list = new ArrayList<>();
        for (String[] f : FileUtil.readAll(FILE)) {
            OrderBatch b = parse(f);
            if (b != null) list.add(b);
        }
        return list;
    }

    public Optional<OrderBatch> findById(String id) {
        for (String[] f : FileUtil.readAll(FILE))
            if (f[0].equals(id)) return Optional.ofNullable(parse(f));
        return Optional.empty();
    }

    public OrderBatch save(OrderBatch b) {
        List<String[]> records = FileUtil.readAll(FILE);
        boolean found = false;
        for (int i = 0; i < records.size(); i++) {
            if (records.get(i)[0].equals(b.getBatchId())) {
                records.set(i, toFields(b)); found = true; break;
            }
        }
        if (!found) {
            if (b.getBatchId() == null || b.getBatchId().isEmpty())
                b.setBatchId(FileUtil.nextId(FILE, "BAT", 0));
            records.add(toFields(b));
        }
        FileUtil.writeAll(FILE, records);
        return b;
    }
}
