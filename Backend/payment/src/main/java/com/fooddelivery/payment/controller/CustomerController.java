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

    @Autowired private PaymentService paymentService;

    @GetMapping
    public String home() { return "customer/home"; }

    @PostMapping("/pay")
    public String pay(@RequestParam String customerId,
                      @RequestParam String orderId,
                      @RequestParam double amount,
                      @RequestParam String paymentType,
                      @RequestParam(required = false) String extraField1,
                      @RequestParam(required = false) String extraField2,
                      RedirectAttributes ra) {
        try {
            paymentService.createPayment(paymentType, orderId, customerId, amount, extraField1, extraField2);
            ra.addFlashAttribute("success", "Payment submitted! Status is PENDING — admin will confirm soon.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Payment failed: " + e.getMessage());
        }
        return "redirect:/customer";
    }

    @PostMapping("/my-payments")
    public String myPayments(@RequestParam String customerId, Model model) {
        model.addAttribute("payments", paymentService.getPaymentsByCustomer(customerId));
        model.addAttribute("customerId", customerId);
        return "customer/my-payments";
    }
}
