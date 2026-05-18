package com.fooddelivery.service;

import com.fooddelivery.model.*;
import com.fooddelivery.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrderService {

    @Autowired private OrderBatchRepository batchRepo;
    @Autowired private OrderRepository      orderRepo;
    @Autowired private OrderItemRepository  itemRepo;

    public List<Order> getAll()                                  { return orderRepo.findAll(); }
    public Optional<Order> getById(String id)                    { return orderRepo.findById(id); }
    public List<Order> getByCustomer(String customerId)          { return orderRepo.findByCustomerId(customerId); }
    public List<Order> getByBatch(String batchId)               { return orderRepo.findByBatchId(batchId); }
    public List<OrderBatch> getAllBatches()                      { return batchRepo.findAll(); }

    @SuppressWarnings("unchecked")
    public OrderBatch placeBatchOrder(Map<String, Object> request) {
        String customerId      = (String) request.get("customerId");
        String deliveryAddress = (String) request.get("deliveryAddress");
        List<Map<String, Object>> items = (List<Map<String, Object>>) request.get("items");

        // Group items by restaurant
        Map<String, List<Map<String, Object>>> byRestaurant = new LinkedHashMap<>();
        for (Map<String, Object> item : items) {
            String rid = (String) item.get("restaurantId");
            byRestaurant.computeIfAbsent(rid, k -> new ArrayList<>()).add(item);
        }

        String batchId   = FileUtil.nextId("order_batches.txt", "BAT", 0);
        String batchDate = LocalDateTime.now().toString();
        double batchTotal = 0;
        List<Order> createdOrders = new ArrayList<>();

        // Create one Order per restaurant
        for (Map.Entry<String, List<Map<String, Object>>> entry : byRestaurant.entrySet()) {
            String restaurantId = entry.getKey();
            List<Map<String, Object>> restItems = entry.getValue();

            double orderTotal = 0;
            String orderId = FileUtil.nextId("orders.txt", "ORD", 0);

            for (Map<String, Object> item : restItems) {
                int    qty   = ((Number) item.get("qty")).intValue();
                double price = ((Number) item.get("unitPrice")).doubleValue();
                orderTotal  += qty * price;

                OrderItem oi = new OrderItem();
                oi.setOrderItemId(FileUtil.nextId("order_items.txt", "OI", 0));
                oi.setOrderId(orderId);
                oi.setItemId((String) item.get("itemId"));
                oi.setQuantity(qty);
                oi.setUnitPrice(price);
                itemRepo.save(oi);
            }

            Order order = new Order();
            order.setOrderId(orderId);
            order.setBatchId(batchId);
            order.setCustomerId(customerId);
            order.setRestaurantId(restaurantId);
            order.setStatus("PENDING");
            order.setOrderDate(batchDate);
            order.setTotalAmount(orderTotal);
            order.setDeliveryAddress(deliveryAddress);
            orderRepo.save(order);
            createdOrders.add(order);
            batchTotal += orderTotal;
        }

        OrderBatch batch = new OrderBatch(batchId, customerId, batchDate, batchTotal, createdOrders.size());
        batchRepo.save(batch);
        return batch;
    }

    public Order updateStatus(String orderId, String status) {
        Order order = orderRepo.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        return orderRepo.save(order);
    }
}
