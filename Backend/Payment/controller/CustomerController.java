package com.fooddelivery.payment.controller;

import com.fooddelivery.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private PaymentService paymentService;



    @GetMapping
    public String customerHome() {
        return "customer/home";
    }



    @PostMapping("/pay")
    public String processPayment(@RequestParam String customerId,
                                  @RequestParam String orderId,
                                  @RequestParam double amount,
                                  @RequestParam String paymentType,
                                  @RequestParam(required = false) String extraField1,
                                  @RequestParam(required = false) String extraField2,
                                  RedirectAttributes redirectAttributes) {
        try {
            paymentService.createPayment(paymentType, orderId, customerId, amount, extraField1, extraField2);
            redirectAttributes.addFlashAttribute("success",
                "Payment submitted successfully! Status is PENDING — admin will confirm soon.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Payment failed: " + e.getMessage());
        }
        return "redirect:/customer";
    }



    @PostMapping("/my-payments")
    public String viewMyPayments(@RequestParam String customerId, Model model) {
        model.addAttribute("payments", paymentService.getPaymentsByCustomer(customerId));
        model.addAttribute("customerId", customerId);
        return "customer/my-payments";
    }
}
