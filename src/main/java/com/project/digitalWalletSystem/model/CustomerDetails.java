package com.project.digitalWalletSystem.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Customer_Details")
public class CustomerDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long customerId;

	@Column(nullable = false)
	@NotNull(message = "customerName must be required")
	@Size(min = 2, max = 30, message = "customerName should be minimum 2 characters")
	private String customerName;

	@Column(nullable = false, unique = true)
	@NotNull(message = "customer Email must be unique")
	@Email(message = "Invalid email format")
	private String customerEmail;

	@Column(nullable = false, unique = true)
	@Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid phone number")
	private String customerPhoneNumber;

	@Column(nullable = false)
	@Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{6,}$", message = "Password must contain at least 1 uppercase, 1 lowercase, 1 number, 1 special character and be at least 6 characters long")
	private String password;

	@Transient
	private String retypePassword;

	@OneToOne(mappedBy = "customerDetails", cascade = CascadeType.ALL)
	@JsonManagedReference
	private Wallet wallet;

}
