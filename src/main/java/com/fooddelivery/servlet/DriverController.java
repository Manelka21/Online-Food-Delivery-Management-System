package com.fooddelivery.servlet;

import com.fooddelivery.model.DeliveryDriver;
import com.fooddelivery.util.FileHandler;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

/**
 Spring MVC Controller for the Delivery Driver subsystem.
 Handles: Register, Login, View Profile, Edit Profile, Delete Account, Accept Order, Complete Order, Update Status.
 */
@Controller
@RequestMapping("/driver")
public class DriverController {

    @Autowired
    private FileHandler fileHandler;

    @GetMapping("/")
    @org.springframework.web.bind.annotation.ResponseBody
    public void redirectRoot(jakarta.servlet.http.HttpServletResponse response) throws java.io.IOException {
        response.sendRedirect("/driver/login");
    }

    //HOME
    @GetMapping({"", "/"})
    public String home(HttpSession session, Model model) {
        DeliveryDriver loggedIn = (DeliveryDriver) session.getAttribute("loggedInDriver");
        if (loggedIn != null) {
            return "redirect:/driver/dashboard";
        }
        return "redirect:/driver/login";
    }

    //REGISTER
    @GetMapping("/register")
    public String registerForm(HttpSession session) {
        if (session.getAttribute("loggedInDriver") != null) return "redirect:/driver/dashboard";
        return "driver/register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String firstName,
                           @RequestParam String lastName,
                           @RequestParam String email,
                           @RequestParam String password,
                           @RequestParam String phone,
                           @RequestParam String vehicleType,
                           @RequestParam String licenseNumber,
                           RedirectAttributes ra) {

        if (firstName.isBlank() || lastName.isBlank() || email.isBlank() ||
                password.isBlank() || phone.isBlank() || licenseNumber.isBlank()) {
            ra.addFlashAttribute("error", "All fields are required.");
            return "redirect:/driver/register";
        }

        String id = fileHandler.generateId();
        String date = LocalDate.now().toString();

        // New drivers default to "ACTIVE" on registration
        DeliveryDriver driver = new DeliveryDriver(
                id, firstName, lastName, email, password,
                phone, vehicleType, licenseNumber, "ACTIVE", date
        );

        boolean created = fileHandler.create(driver);
        if (!created) {
            ra.addFlashAttribute("error", "An account with this email already exists.");
            return "redirect:/driver/register";
        }

        ra.addFlashAttribute("success", "Account created successfully! Please log in.");
        return "redirect:/driver/login";
    }

    //LOGIN
    @GetMapping("/login")
    public String loginForm(HttpSession session) {
        if (session.getAttribute("loggedInDriver") != null) return "redirect:/driver/dashboard";
        return "driver/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes ra) {

        DeliveryDriver driver = fileHandler.findByEmail(email);

        if (driver == null || !driver.getPassword().equals(password)) {
            ra.addFlashAttribute("error", "Invalid email or password.");
            return "redirect:/driver/login";
        }

        session.setAttribute("loggedInDriver", driver);
        return "redirect:/driver/dashboard";
    }

    //LOGOUT
    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes ra) {
        session.invalidate();
        ra.addFlashAttribute("success", "Logged out successfully.");
        return "redirect:/driver/login";
    }

    //DASHBOARD
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        DeliveryDriver driver = (DeliveryDriver) session.getAttribute("loggedInDriver");
        if (driver == null) return "redirect:/driver/login";

        // Refresh from text file database to load accurate realtime properties
        DeliveryDriver fresh = fileHandler.findById(driver.getDriverId());
        if (fresh != null) {
            session.setAttribute("loggedInDriver", fresh);
            model.addAttribute("driver", fresh);
        } else {
            model.addAttribute("driver", driver);
        }

        // Variable to trigger the sidebar highlight style matching rules
        model.addAttribute("currentPage", "dashboard");

        // Realtime delivery assignment routing logic tokens
        String currentActiveOrder = (String) session.getAttribute("activeOrderId");
        if (currentActiveOrder != null) {
            model.addAttribute("activeOrder", currentActiveOrder);
        }

        return "driver/dashboard";
    }

    // POST: ACCEPT ORDER TOGGLE
    @PostMapping("/acceptOrder")
    public String acceptOrder(@RequestParam String orderId, HttpSession session, RedirectAttributes ra) {
        DeliveryDriver driver = (DeliveryDriver) session.getAttribute("loggedInDriver");
        if (driver == null) return "redirect:/driver/login";

        // Assign the active order code into the driver's current session state
        session.setAttribute("activeOrderId", orderId);
        ra.addFlashAttribute("success", "Order " + orderId + " accepted successfully! Drive safely.");
        return "redirect:/driver/dashboard";
    }

    // POST: COMPLETE ORDER TOGGLE
    @PostMapping("/completeOrder")
    public String completeOrder(HttpSession session, RedirectAttributes ra) {
        DeliveryDriver driver = (DeliveryDriver) session.getAttribute("loggedInDriver");
        if (driver == null) return "redirect:/driver/login";

        // Remove active order from session, clearing the track panel
        session.removeAttribute("activeOrderId");
        ra.addFlashAttribute("success", "Trip completed successfully!");
        return "redirect:/driver/dashboard";
    }

    // POST: UPDATE DRIVER DUTY STATUS
    @PostMapping("/updateStatus")
    public String updateStatus(@RequestParam String status, HttpSession session, RedirectAttributes ra) {
        DeliveryDriver driver = (DeliveryDriver) session.getAttribute("loggedInDriver");
        if (driver == null) return "redirect:/driver/login";

        // Modify local object reference and overwrite text file layout records
        driver.setStatus(status);
        boolean updated = fileHandler.update(driver);

        if (updated) {
            session.setAttribute("loggedInDriver", driver);
            ra.addFlashAttribute("success", "Your system availability status is now set to " + status + ".");
        } else {
            ra.addFlashAttribute("error", "Failed to update platform status registry.");
        }
        return "redirect:/driver/dashboard";
    }

    //VIEW PROFILE
    @GetMapping("/profile")
    public String viewProfile(HttpSession session, Model model) {
        DeliveryDriver driver = (DeliveryDriver) session.getAttribute("loggedInDriver");
        if (driver == null) return "redirect:/driver/login";

        DeliveryDriver fresh = fileHandler.findById(driver.getDriverId());
        model.addAttribute("driver", fresh != null ? fresh : driver);
        model.addAttribute("currentPage", "profile");
        return "driver/profile";
    }

    //EDIT PROFILE
    @GetMapping("/edit")
    public String editForm(HttpSession session, Model model) {
        DeliveryDriver driver = (DeliveryDriver) session.getAttribute("loggedInDriver");
        if (driver == null) return "redirect:/driver/login";

        DeliveryDriver fresh = fileHandler.findById(driver.getDriverId());
        model.addAttribute("driver", fresh != null ? fresh : driver);
        model.addAttribute("currentPage", "edit");
        return "driver/edit";
    }

    @PostMapping("/edit")
    public String editSave(@RequestParam String firstName,
                           @RequestParam String lastName,
                           @RequestParam String phone,
                           @RequestParam String vehicleType,
                           @RequestParam String licenseNumber,
                           @RequestParam(required = false) String newPassword,
                           @RequestParam(required = false) String status,
                           HttpSession session,
                           RedirectAttributes ra) {

        DeliveryDriver driver = (DeliveryDriver) session.getAttribute("loggedInDriver");
        if (driver == null) return "redirect:/driver/login";

        driver.setFirstName(firstName);
        driver.setLastName(lastName);
        driver.setPhone(phone);
        driver.setVehicleType(vehicleType);
        driver.setLicenseNumber(licenseNumber);
        if (status != null && !status.isBlank()) driver.setStatus(status);
        if (newPassword != null && !newPassword.isBlank()) driver.setPassword(newPassword);

        boolean updated = fileHandler.update(driver);
        if (updated) {
            session.setAttribute("loggedInDriver", driver);
            ra.addFlashAttribute("success", "Profile updated successfully.");
        } else {
            ra.addFlashAttribute("error", "Failed to update profile. Please try again.");
        }
        return "redirect:/driver/profile";
    }

    //DELETE ACCOUNT
    @GetMapping("/delete")
    public String deleteConfirm(HttpSession session, Model model) {
        DeliveryDriver driver = (DeliveryDriver) session.getAttribute("loggedInDriver");
        if (driver == null) return "redirect:/driver/login";
        model.addAttribute("driver", driver);
        return "driver/delete";
    }

    @PostMapping("/delete")
    public String deleteAccount(@RequestParam String confirmPassword,
                                HttpSession session,
                                RedirectAttributes ra) {

        DeliveryDriver driver = (DeliveryDriver) session.getAttribute("loggedInDriver");
        if (driver == null) return "redirect:/driver/login";

        if (!driver.getPassword().equals(confirmPassword)) {
            ra.addFlashAttribute("error", "Password does not match. Account not deleted.");
            return "redirect:/driver/delete";
        }

        boolean deleted = fileHandler.delete(driver.getDriverId());
        if (deleted) {
            session.invalidate();
            ra.addFlashAttribute("success", "Account deleted successfully.");
            return "redirect:/driver/login";
        } else {
            ra.addFlashAttribute("error", "Failed to delete account.");
            return "redirect:/driver/delete";
        }
    }

    //ADMIN
    @GetMapping("/all")
    public String listAll(Model model) {
        List<DeliveryDriver> drivers = fileHandler.readAll();
        model.addAttribute("drivers", drivers);
        return "driver/all";
    }
}