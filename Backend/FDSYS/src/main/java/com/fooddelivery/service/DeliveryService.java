package com.fooddelivery.service;

import com.fooddelivery.model.*;
import com.fooddelivery.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class DeliveryService {

    @Autowired private DriverRepository   driverRepo;
    @Autowired private DeliveryRepository deliveryRepo;

    public List<Driver>   getAllDrivers()                         { return driverRepo.findAll(); }
    public Optional<Driver> getDriverById(String id)              { return driverRepo.findById(id); }
    public Driver saveDriver(Driver d)                            { return driverRepo.save(d); }
    public void deleteDriver(String id)                           { driverRepo.deleteById(id); }

    public List<Delivery> getAllDeliveries()                      { return deliveryRepo.findAll(); }
    public Optional<Delivery> getByOrderId(String orderId)        { return deliveryRepo.findByOrderId(orderId); }

    public Delivery assignDelivery(String orderId, String driverId) {
        Delivery delivery = new Delivery();
        delivery.setOrderId(orderId);
        delivery.setDriverId(driverId);
        delivery.setAssignedTime(LocalDateTime.now().toString());
        delivery.setStatus("ASSIGNED");
        return deliveryRepo.save(delivery);
    }

    public Delivery updateStatus(String id, String status) {
        Delivery d = deliveryRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Delivery not found"));
        d.setStatus(status);
        if ("DELIVERED".equals(status)) d.setDeliveredTime(LocalDateTime.now().toString());
        return deliveryRepo.save(d);
    }

    public Driver updateDriver(String id, Driver updated) {
        Driver existing = driverRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Driver not found"));
        existing.setName(updated.getName());
        existing.setPhone(updated.getPhone());
        existing.setVehicleNumber(updated.getVehicleNumber());
        existing.setLicenseNumber(updated.getLicenseNumber());
        existing.setAvailable(updated.isAvailable());
        return driverRepo.save(existing);
    }
}
