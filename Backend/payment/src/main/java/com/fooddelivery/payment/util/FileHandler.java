package com.fooddelivery.payment.util;

import com.fooddelivery.payment.model.*;
import org.springframework.stereotype.Component;
import java.io.*;
import java.util.*;

@Component
public class FileHandler {
    private static final String FILE_PATH = "src/main/resources/data/payments.txt";

    public void appendPayment(Payment payment) {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            w.write(payment.toFileString()); w.newLine();
        } catch (IOException e) { System.err.println("Error writing: " + e.getMessage()); }
    }

    public List<Payment> loadAllPayments() {
        List<Payment> payments = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) return payments;
        try (BufferedReader r = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = r.readLine()) != null) {
                if (!line.trim().isEmpty()) { Payment p = parseLine(line); if (p != null) payments.add(p); }
            }
        } catch (IOException e) { System.err.println("Error reading: " + e.getMessage()); }
        return payments;
    }

    private Payment parseLine(String line) {
        try {
            String[] p = line.split(",");
            String paymentId=p[0], orderId=p[1], customerId=p[2], status=p[4], createdAt=p[5], type=p[6], f1=p[7];
            double amount = Double.parseDouble(p[3]);
            String f2 = p.length > 8 ? p[8] : "N/A";
            switch (type) {
                case "CARD":   return new CardPayment(paymentId, orderId, customerId, amount, status, createdAt, f1, f2);
                case "CASH":   return new CashPayment(paymentId, orderId, customerId, amount, status, createdAt, f1);
                case "ONLINE": return new OnlinePayment(paymentId, orderId, customerId, amount, status, createdAt, f1);
                default: return null;
            }
        } catch (Exception e) { return null; }
    }

    public void saveAllPayments(List<Payment> payments) {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
            for (Payment p : payments) { w.write(p.toFileString()); w.newLine(); }
        } catch (IOException e) { System.err.println("Error saving: " + e.getMessage()); }
    }
}
