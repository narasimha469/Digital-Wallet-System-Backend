package com.project.digitalWalletSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.digitalWalletSystem.model.Transaction;
import com.project.digitalWalletSystem.service.TransactionService;

@RestController
@RequestMapping("/digitalWalletSystem/transactions")
@CrossOrigin(origins = "*")
public class TransactionController {
	
	@Autowired
	TransactionService transactionService;
	
    @GetMapping("/{walletId}")
	List<Transaction> findByWalletWalletIdOrderByTransactionTimeDesc(@PathVariable Long walletId){
		return transactionService.getAllTransactionsByWalletId(walletId);
	}

}
