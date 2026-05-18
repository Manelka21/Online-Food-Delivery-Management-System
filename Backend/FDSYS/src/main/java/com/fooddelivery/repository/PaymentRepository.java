package com.fooddelivery.repository;

import com.fooddelivery.model.Payment;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class PaymentRepository {

    private static final String FILE = "payments.txt";

    private Payment parse(String[] f) {
        if (f.length < 6) return null;
        Payment p = new Payment();
        p.setPaymentId(f[0]);
        p.setOrderId(f[1]);
        p.setAmount(Double.parseDouble(f[2]));
        p.setMethod(f[3]);
        p.setStatus(f[4]);
        p.setPaymentDate(f[5].isEmpty() ? null : f[5]);
        return p;
    }

    private String[] toFields(Payment p) {
        return new String[]{
            p.getPaymentId(), p.getOrderId(), String.valueOf(p.getAmount()),
            p.getMethod(), p.getStatus(),
            p.getPaymentDate() != null ? p.getPaymentDate() : ""
        };
    }

    public List<Payment> findAll() {
        List<Payment> list = new ArrayList<>();
        for (String[] f : FileUtil.readAll(FILE)) {
            Payment p = parse(f); if (p != null) list.add(p);
        }
        return list;
    }

    public Optional<Payment> findById(String id) {
        for (String[] f : FileUtil.readAll(FILE))
            if (f[0].equals(id)) return Optional.ofNullable(parse(f));
        return Optional.empty();
    }

    public Optional<Payment> findByOrderId(String orderId) {
        for (String[] f : FileUtil.readAll(FILE))
            if (f.length >= 2 && f[1].equals(orderId))
                return Optional.ofNullable(parse(f));
        return Optional.empty();
    }

    public Payment save(Payment p) {
        List<String[]> records = FileUtil.readAll(FILE);
        boolean found = false;
        for (int i = 0; i < records.size(); i++) {
            if (records.get(i)[0].equals(p.getPaymentId())) {
                records.set(i, toFields(p)); found = true; break;
            }
        }
        if (!found) {
            if (p.getPaymentId() == null || p.getPaymentId().isEmpty())
                p.setPaymentId(FileUtil.nextId(FILE, "PAY", 0));
            records.add(toFields(p));
        }
        FileUtil.writeAll(FILE, records);
        return p;
    }

    public void deleteById(String id) {
        List<String[]> records = FileUtil.readAll(FILE);
        records.removeIf(f -> f[0].equals(id));
        FileUtil.writeAll(FILE, records);
    }
}
