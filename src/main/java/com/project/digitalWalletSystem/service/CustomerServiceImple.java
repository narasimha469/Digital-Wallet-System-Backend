package com.project.digitalWalletSystem.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import com.project.digitalWalletSystem.repository.WalletRepository;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.project.digitalWalletSystem.exception.CustomerNotFoundException;
import com.project.digitalWalletSystem.exception.DuplicateResourceException;
import com.project.digitalWalletSystem.model.CustomerDetails;
import com.project.digitalWalletSystem.model.Wallet;
import com.project.digitalWalletSystem.repository.CustomerRepository;

@Service
public class CustomerServiceImple implements CustomerService {

	private CustomerRepository customerRepository;
	private WalletRepository walletRepository;

	public CustomerServiceImple(CustomerRepository customerRepository, WalletRepository walletRepository) {
		this.customerRepository = customerRepository;
		this.walletRepository = walletRepository;
	}

	@Override
	@Transactional
	public String forgotPassword(String email, String newPassword) {
	    CustomerDetails customer = customerRepository.findByCustomerEmail(email);

	    if (customer == null) {
	        throw new CustomerNotFoundException("Customer email not found");
	    }

	 // backend password validation
	    if (newPassword == null || 
	        newPassword.length() < 8 || 
	        !newPassword.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}$")) {
	        throw new IllegalArgumentException(
	            "Password must be at least 8 characters long and contain at least one uppercase letter, " +
	            "one lowercase letter, one digit, and one special character"
	        );
	    }


	    customer.setPassword(newPassword); // update password
	    customerRepository.save(customer); // persist changes

	    return "Password reset successfully";
	}


	@Override
	@Transactional
	public CustomerDetails createCustomer(CustomerDetails customerDetails) {

		if (customerRepository.existsByCustomerEmail(customerDetails.getCustomerEmail())) {
			throw new DuplicateResourceException("Email already exists!");
		}
		if (customerRepository.existsByCustomerPhoneNumber(customerDetails.getCustomerPhoneNumber())) {
			throw new DuplicateResourceException("Phone number already exists!");
		}
		CustomerDetails savedCustomer = customerRepository.save(customerDetails);
		Wallet wallet = new Wallet();
		wallet.setBalance(BigDecimal.valueOf(0.00));
		wallet.setCustomerDetails(savedCustomer);
		walletRepository.save(wallet);

		return savedCustomer;
	}

	@Override
	public CustomerDetails login(String email, String password) {
		CustomerDetails customer = customerRepository.findByCustomerEmail(email);
		if (customer == null) {
			throw new RuntimeException("Wrong email or password");
		}
		if (!customer.getPassword().equals(password)) {
			throw new CustomerNotFoundException("Wrong email or password");
		}
		return customer;
	}

	@Override
	public List<CustomerDetails> getAllCustomerDetails() {
		return customerRepository.findAll();
	}

	@Override
	public CustomerDetails getCustomerById(Long customerId) {
		Optional<CustomerDetails> customerOptional = customerRepository.findById(customerId);
		if (customerOptional.isPresent()) {
			return customerOptional.get();
		} else {
			throw new CustomerNotFoundException("customer not found with this ID:" + customerId);
		}

	}

	@Override
	public CustomerDetails updateCustomerDetails(Long customerId, CustomerDetails customerDetails) {
		Optional<CustomerDetails> customerOptional = customerRepository.findById(customerId);
		if (customerOptional.isPresent()) {
			CustomerDetails existingCustomer = customerOptional.get();
			existingCustomer.setCustomerName(customerDetails.getCustomerName());
			existingCustomer.setCustomerEmail(customerDetails.getCustomerEmail());
			existingCustomer.setCustomerPhoneNumber(customerDetails.getCustomerPhoneNumber());
			return customerRepository.save(existingCustomer);
		} else {
			throw new CustomerNotFoundException("customer not found with this ID:" + customerId);
		}

	}

	@Override
	public String deleteCustomerById(Long customerId) {
		Optional<CustomerDetails> customerOptional = customerRepository.findById(customerId);
		if (customerOptional.isPresent()) {
			customerRepository.deleteById(customerId);
			return "Customer deleted Successfully";

		} else {
			throw new CustomerNotFoundException("customer not found with this ID:" + customerId);
		}
	}

}
