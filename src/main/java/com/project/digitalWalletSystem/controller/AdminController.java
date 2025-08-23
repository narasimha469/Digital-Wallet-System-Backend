package com.project.digitalWalletSystem.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.digitalWalletSystem.service.AdminService;

@RestController
@RequestMapping("/digitalWalletSystem/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        if (adminService.login(username, password)) {
            return ResponseEntity.ok("Admin login successful"); // 200 OK
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED) // 401 Unauthorized
                                 .body("Invalid username or password");
        }
    }

}
