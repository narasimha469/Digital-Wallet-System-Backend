package com.project.digitalWalletSystem.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.digitalWalletSystem.model.Transaction;
import com.project.digitalWalletSystem.model.Wallet;
import com.project.digitalWalletSystem.repository.TransactionRepository;

@Service
public class TransactionServiceImple implements TransactionService{
	
@Autowired
TransactionRepository transactionRepository;

@Override
public Transaction saveTransactions(Wallet wallet,BigDecimal amount,String transactionType,Boolean isSuccess) {
	Transaction trans = new Transaction();
	trans.setWallet(wallet);
	trans.setAmount(amount);
	trans.setTransactionType(transactionType);
	trans.setTransactionTime(LocalDateTime.now());
	trans.setStatus(isSuccess?"SUCCESS":"FAILURE");
	return transactionRepository.save(trans);
}

@Override
public List<Transaction>getAllTransactionsByWalletId(Long walletId){
	return transactionRepository.findByWalletWalletIdOrderByTransactionTimeDesc(walletId);
}
	

}
