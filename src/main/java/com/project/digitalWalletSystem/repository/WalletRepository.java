package com.project.digitalWalletSystem.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.digitalWalletSystem.model.Wallet;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long>{
	
	public Wallet findByCustomerDetailsCustomerId(Long customerId);
	

}
