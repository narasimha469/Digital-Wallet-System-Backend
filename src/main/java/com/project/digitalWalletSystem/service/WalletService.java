package com.project.digitalWalletSystem.service;

import java.math.BigDecimal;
import java.util.List;

import com.project.digitalWalletSystem.model.Wallet;

public interface WalletService {
	
	public Wallet getWalletByCustomerId(Long customerId);
	public Wallet addBalance(BigDecimal amount, Long customerId);
	public Wallet deductBalance(BigDecimal amount,Long customerId);
	public BigDecimal getCurrentBalance(Long customerId);
	public Wallet getWalletById(Long walletId);
	public List<Wallet>getAllWalletDetails();
	

}
