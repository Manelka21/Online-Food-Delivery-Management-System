package com.fooddelivery.payment.controller;

import com.fooddelivery.payment.model.Payment;
import com.fooddelivery.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;



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
        Optional<Payment> payment = paymentService.getPaymentById(id);
        if (payment.isPresent()) {
            model.addAttribute("payment", payment.get());
            return "payments/view";
        }
        return "redirect:/payments";
    }



    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        Optional<Payment> payment = paymentService.getPaymentById(id);
        if (payment.isPresent()) {
            model.addAttribute("payment", payment.get());
            return "payments/edit";
        }
        return "redirect:/payments";
    }



    @PostMapping("/edit/{id}")
    public String updatePayment(@PathVariable String id,
                                 @RequestParam String status,
                                 RedirectAttributes redirectAttributes) {
        boolean updated = paymentService.updatePaymentStatus(id, status);
        if (updated) {
            redirectAttributes.addFlashAttribute("success", "Payment status updated successfully!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Payment not found.");
        }
        return "redirect:/payments";
    }



    @PostMapping("/delete/{id}")
    public String deletePayment(@PathVariable String id, RedirectAttributes redirectAttributes) {
        boolean deleted = paymentService.deletePayment(id);
        if (deleted) {
            redirectAttributes.addFlashAttribute("success", "Payment deleted successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Payment not found.");
        }
        return "redirect:/payments";
    }



    @GetMapping("/filter")
    public String filterByStatus(@RequestParam String status, Model model) {
        List<Payment> payments = paymentService.getPaymentsByStatus(status);
        model.addAttribute("payments", payments);
        model.addAttribute("filterStatus", status);
        model.addAttribute("totalRevenue", paymentService.getTotalRevenue());
        model.addAttribute("totalCount", payments.size());
        model.addAttribute("completedCount", payments.stream().filter(p -> p.getStatus().equals("COMPLETED")).count());
        model.addAttribute("pendingCount", payments.stream().filter(p -> p.getStatus().equals("PENDING")).count());
        return "payments/index";
    }
}
