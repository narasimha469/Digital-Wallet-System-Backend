package com.project.digitalWalletSystem.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.digitalWalletSystem.exception.InsufficientBalanceException;
import com.project.digitalWalletSystem.exception.WalletNotFoundException;
import com.project.digitalWalletSystem.model.CustomerDetails;
import com.project.digitalWalletSystem.model.Wallet;
import com.project.digitalWalletSystem.repository.CustomerRepository;
import com.project.digitalWalletSystem.repository.WalletRepository;

@Service
public class WalletServiceImple implements WalletService {

	private WalletRepository walletRepository;
	private TransactionService transactionService;
	private CustomerRepository customerRepository;

	public WalletServiceImple(WalletRepository walletRepository, TransactionService transactionService,
			CustomerRepository customerRepository) {
		this.walletRepository = walletRepository;
		this.transactionService = transactionService;
		this.customerRepository = customerRepository;
	}

	// Helper method to ensure wallet exists
	private Wallet ensureWalletExists(Long customerId) {
		Wallet wallet = walletRepository.findByCustomerDetailsCustomerId(customerId);
		if (wallet == null) {
			CustomerDetails customer = customerRepository.findById(customerId)
					.orElseThrow(() -> new RuntimeException("Customer not found with id " + customerId));
			wallet = new Wallet();
			wallet.setBalance(BigDecimal.ZERO);
			wallet.setCustomerDetails(customer);
			wallet = walletRepository.save(wallet);
		}
		return wallet;
	}

	@Override
	public Wallet getWalletByCustomerId(Long customerId) {
		Wallet wallet = walletRepository.findByCustomerDetailsCustomerId(customerId);
		if (wallet == null) {
			throw new WalletNotFoundException("Wallet not found for customerId:" + customerId);
		}
		return wallet;
	}

	@Override
	public Wallet addBalance(BigDecimal amount, Long customerId) {
		try {
			Wallet wallet = ensureWalletExists(customerId); // ensure wallet exists
			wallet.setBalance(wallet.getBalance().add(amount));
			Wallet updatedWallet = walletRepository.save(wallet);
			transactionService.saveTransactions(updatedWallet, amount, "CREDIT", true);
			return updatedWallet;
		} catch (Exception ex) {
			Wallet wallet = ensureWalletExists(customerId);
			transactionService.saveTransactions(wallet, amount, "CREDIT", false);
			throw new RuntimeException("Failed to add balance: " + ex.getMessage());
		}
	}

	@Override
	public Wallet deductBalance(BigDecimal amount, Long customerId) {
		Wallet wallet = ensureWalletExists(customerId); // ensure wallet exists
		if (wallet.getBalance().compareTo(amount) < 0) {
			transactionService.saveTransactions(wallet, amount, "DEBIT", false);
			throw new InsufficientBalanceException("Not enough balance to deduct");
		}
		wallet.setBalance(wallet.getBalance().subtract(amount));
		Wallet updatedWallet = walletRepository.save(wallet);
		transactionService.saveTransactions(updatedWallet, amount, "DEBIT", true);
		return updatedWallet;
	}

	@Override
	public BigDecimal getCurrentBalance(Long customerId) {
		Wallet wallet = ensureWalletExists(customerId);
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
