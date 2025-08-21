package com.project.digitalWalletSystem.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "wallets")
public class Wallet {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long walletId;
	@Column
	@DecimalMin(value="0.0",inclusive=true,message="balance should not go negative")
	private BigDecimal balance;
	
	@OneToOne
	@JoinColumn(name="customer_Id", referencedColumnName="customerId")
	@JsonBackReference // keeps customer from serializing wallet again
	private CustomerDetails customerDetails;

	@OneToMany(mappedBy ="wallet", cascade = CascadeType.ALL)
	@JsonIgnoreProperties("wallet") // ignore wallet back-reference in transactions
	private List<Transaction> transactions = new ArrayList<>();

}
