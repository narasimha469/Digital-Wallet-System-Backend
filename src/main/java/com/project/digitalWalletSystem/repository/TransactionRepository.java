package com.project.digitalWalletSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.digitalWalletSystem.model.Transaction;
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>{
	
	List<Transaction>findByWalletWalletIdOrderByTransactionTimeDesc(Long walletId);

}
