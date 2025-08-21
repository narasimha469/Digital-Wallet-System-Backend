package com.project.digitalWalletSystem.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Transaction_Details")
public class Transaction {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long transactionId;
	
	
	private String transactionType;
	
	private BigDecimal amount;
	
	private LocalDateTime transactionTime;
	
	private String status;
	
	@ManyToOne
	@JoinColumn(name="wallet_Id", nullable=false)
	@JsonIgnoreProperties("transactions") // prevents infinite recursion
	private Wallet wallet;

}
