package com.fooddelivery.payment.controller;

import com.fooddelivery.payment.model.Payment;
import com.fooddelivery.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.*;

@Controller
@RequestMapping("/payments")
public class PaymentController {

    @Autowired private PaymentService paymentService;

    @GetMapping
    public String dashboard(Model model) {
        List<Payment> payments = paymentService.getAllPayments();
        model.addAttribute("payments", payments);
        model.addAttribute("totalRevenue", paymentService.getTotalRevenue());
        model.addAttribute("totalCount", payments.size());
        model.addAttribute("completedCount", payments.stream().filter(p -> p.getStatus().equals("COMPLETED")).count());
        model.addAttribute("pendingCount", payments.stream().filter(p -> p.getStatus().equals("PENDING")).count());
        return "payments/index";
    }

    @GetMapping("/view/{id}")
    public String viewPayment(@PathVariable String id, Model model) {
        Optional<Payment> p = paymentService.getPaymentById(id);
        if (p.isPresent()) { model.addAttribute("payment", p.get()); return "payments/view"; }
        return "redirect:/payments";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        Optional<Payment> p = paymentService.getPaymentById(id);
        if (p.isPresent()) { model.addAttribute("payment", p.get()); return "payments/edit"; }
        return "redirect:/payments";
    }

    @PostMapping("/edit/{id}")
    public String updatePayment(@PathVariable String id, @RequestParam String status, RedirectAttributes ra) {
        paymentService.updatePaymentStatus(id, status);
        ra.addFlashAttribute("success", "Payment status updated!");
        return "redirect:/payments";
    }

    @PostMapping("/delete/{id}")
    public String deletePayment(@PathVariable String id, RedirectAttributes ra) {
        paymentService.deletePayment(id);
        ra.addFlashAttribute("success", "Payment deleted.");
        return "redirect:/payments";
    }

    @GetMapping("/filter")
    public String filter(@RequestParam String status, Model model) {
        List<Payment> payments = paymentService.getPaymentsByStatus(status);
        model.addAttribute("payments", payments);
        model.addAttribute("totalRevenue", paymentService.getTotalRevenue());
        model.addAttribute("totalCount", payments.size());
        model.addAttribute("completedCount", payments.stream().filter(p -> p.getStatus().equals("COMPLETED")).count());
        model.addAttribute("pendingCount", payments.stream().filter(p -> p.getStatus().equals("PENDING")).count());
        return "payments/index";
    }
}
