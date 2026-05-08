package com.fooddelivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class FoodDeliverySystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(FoodDeliverySystemApplication.class, args);
    }
}