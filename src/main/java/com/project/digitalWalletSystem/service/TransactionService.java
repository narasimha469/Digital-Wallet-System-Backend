package com.project.digitalWalletSystem.service;

import java.math.BigDecimal;
import java.util.List;

import com.project.digitalWalletSystem.model.Transaction;
import com.project.digitalWalletSystem.model.Wallet;


public interface TransactionService {
	
	public Transaction saveTransactions(Wallet wallet,BigDecimal amount,String transactionType,Boolean isSuccess);
    public List<Transaction>getAllTransactionsByWalletId(Long walletId);
}
