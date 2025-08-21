package com.project.digitalWalletSystem.controller;

import org.springframework.web.bind.annotation.*;
import com.project.digitalWalletSystem.service.AdminService;

import java.util.Map;

@RestController
@RequestMapping("/digitalWalletSystem/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        if (adminService.login(username, password)) {
            return "Admin login successful";
        } else {
            return "Invalid username or password";
        }
    }
}
