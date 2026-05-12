package com.fooddelivery.payment.util;

import com.fooddelivery.payment.model.*;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileHandler {

    private static final String FILE_PATH = "src/main/resources/data/payments.txt";

    public void saveAllPayments(List<Payment> payments) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
            for (Payment p : payments) {
                writer.write(p.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing: " + e.getMessage());
        }
    }

    public List<Payment> loadAllPayments() {
        List<Payment> payments = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) return payments;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                Payment p = parseLine(line);
                if (p != null) payments.add(p);
            }
        } catch (IOException e) {
            System.err.println("Error reading: " + e.getMessage());
        }
        return payments;
    }

        private Payment parseLine(String line) {
        try {
            String[] p = line.split(",");
            String paymentId  = p[0];
            String orderId    = p[1];
            String customerId = p[2];
            double amount     = Double.parseDouble(p[3]);
            String status     = p[4];
            String createdAt  = p[5];
            String type       = p[6];
            String field1     = p[7];
            String field2     = p.length > 8 ? p[8] : "N/A";

            switch (type) {
                case "CARD":   return new CardPayment(paymentId, orderId, customerId, amount, status, createdAt, field1, field2);
                case "CASH":   return new CashPayment(paymentId, orderId, customerId, amount, status, createdAt, field1);
                case "ONLINE": return new OnlinePayment(paymentId, orderId, customerId, amount, status, createdAt, field1);
                default: return null;
            }
        } catch (Exception e) {
            System.err.println("Skipping bad line: " + line);
            return null;
        }
    }

    public void appendPayment(Payment payment) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(payment.toFileString());
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error appending: " + e.getMessage());
        }
    }
}
