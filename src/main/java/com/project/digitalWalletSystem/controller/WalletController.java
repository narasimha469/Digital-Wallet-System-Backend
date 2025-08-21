package com.project.digitalWalletSystem.controller;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.project.digitalWalletSystem.model.Wallet;
import com.project.digitalWalletSystem.service.WalletService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/digitalWalletSystem/wallets")
public class WalletController {

    @Autowired
    private WalletService walletService;

    
    @GetMapping("/customer/{customerId}")
    public Wallet getWalletByCustomerId(@PathVariable Long customerId) {
        return walletService.getWalletByCustomerId(customerId);
    }

  
    @PutMapping("/add/{customerId}")
    public Wallet addBalance(@Valid @RequestBody  BigDecimal amount, @PathVariable Long customerId) {
        return walletService.addBalance(amount, customerId);
    }

    
    @PutMapping("/deduct/{customerId}")
    public Wallet deductBalance(@Valid @RequestBody  BigDecimal amount, @PathVariable Long customerId) {
        return walletService.deductBalance(amount, customerId);
    }

    
    @GetMapping("/getBalance/{customerId}")
    public BigDecimal getCurrentBalance(@PathVariable Long customerId) {
        return walletService.getCurrentBalance(customerId);
    }

    
    @GetMapping("/walletId/{walletId}")
    public Wallet getWalletById(@PathVariable Long walletId) {
        return walletService.getWalletById(walletId);
    }

    
    @GetMapping
    public List<Wallet> getAllWalletDetails() {
        return walletService.getAllWalletDetails();
    }
}
