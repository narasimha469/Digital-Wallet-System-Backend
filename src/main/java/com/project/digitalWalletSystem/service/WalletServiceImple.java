package com.project.digitalWalletSystem.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


import org.springframework.stereotype.Service;

import com.project.digitalWalletSystem.exception.InsufficientBalanceException;
import com.project.digitalWalletSystem.exception.WalletNotFoundException;
import com.project.digitalWalletSystem.model.Wallet;
import com.project.digitalWalletSystem.repository.WalletRepository;

@Service
public class WalletServiceImple implements WalletService {

	private WalletRepository walletRepository;
	private TransactionService transactionService;
	
	public WalletServiceImple(WalletRepository walletRepository,TransactionService transactionService) {
		this.walletRepository=walletRepository;
		this.transactionService=transactionService;
	}
	
	
	@Override
	public Wallet getWalletByCustomerId(Long customerId) {
		Wallet wallet = walletRepository.findByCustomerDetailsCustomerId(customerId);
		if (wallet == null) {
			throw new WalletNotFoundException("Wallet not found for  customerId:" + customerId);
		}
		return wallet;

	}

	@Override
	public Wallet addBalance(BigDecimal amount, Long customerId) {
		try {
		Wallet wallet = getWalletByCustomerId(customerId);
		wallet.setBalance(wallet.getBalance().add(amount));
		Wallet updatedWallet = walletRepository.save(wallet);
		transactionService.saveTransactions(updatedWallet, amount,"CREDIT", true);
		return updatedWallet;
		}
		catch(Exception ex) {
			Wallet wallet = getWalletByCustomerId(customerId);
			transactionService.saveTransactions(wallet, amount,"CREDIT", false);
			throw new RuntimeException("Failed to add balance"+ex.getMessage());
		}

	}

	@Override
	public Wallet deductBalance(BigDecimal amount, Long customerId) {
		Wallet wallet = getWalletByCustomerId(customerId);
		if (wallet.getBalance().compareTo(amount) < 0) {
			transactionService.saveTransactions(wallet, amount, "DEBIT", false);
			throw new InsufficientBalanceException("Not enough balance to deduct");
		}
		wallet.setBalance(wallet.getBalance().subtract(amount));
		Wallet updatedWallet = walletRepository.save(wallet);
		transactionService.saveTransactions(updatedWallet, amount, "DEBIT",true);
		return updatedWallet;
	}

	@Override
	public BigDecimal getCurrentBalance(Long customerId) {
		Wallet wallet = getWalletByCustomerId(customerId);
		return wallet.getBalance();
	}

	@Override
	public Wallet getWalletById(Long walletId) {
		Optional<Wallet> wallet = walletRepository.findById(walletId);
		if (wallet.isPresent()) {
			return wallet.get();
		} else {
			throw new WalletNotFoundException("Wallet not found for walletID:" + walletId);
		}
	}

	@Override
	public List<Wallet> getAllWalletDetails() {
		return walletRepository.findAll();
	}

}
