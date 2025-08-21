package com.project.digitalWalletSystem.service;

import java.util.List;

import com.project.digitalWalletSystem.model.CustomerDetails;

public interface CustomerService {
	
	public CustomerDetails  createCustomer(CustomerDetails customerDetails);
	public List<CustomerDetails> getAllCustomerDetails();
	public CustomerDetails getCustomerById(Long customerId);
	public CustomerDetails updateCustomerDetails(Long customerId,CustomerDetails customerDetails);
	public String deleteCustomerById(Long customerId);
	public  String forgotPassword(String email, String newPassword);
	public CustomerDetails login(String email, String password);

}
