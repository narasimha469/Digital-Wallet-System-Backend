package com.project.digitalWalletSystem.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.digitalWalletSystem.model.CustomerDetails;
import com.project.digitalWalletSystem.service.CustomerService;

import jakarta.validation.Valid;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/digitalWalletSystem/customers")
public class CustomerController {
	
@Autowired
CustomerService customerService;

@PostMapping
public CustomerDetails  createCustomer(@Valid @RequestBody CustomerDetails customerDetails) {
	return customerService.createCustomer(customerDetails);
}



//Forgot Password
@PostMapping("/forgot-password")
public String forgotPassword(@RequestBody Map<String, String> request) {
 return customerService.forgotPassword(request.get("email"), request.get("newPassword"));
}

@PostMapping("/login") 
public CustomerDetails login(@RequestBody Map<String, String> loginData) { 
	String email = loginData.get("email"); 
	String password = loginData.get("password");
	return customerService.login(email, password);
}


@GetMapping
public List<CustomerDetails> getAllCustomerDetails(){
	return customerService.getAllCustomerDetails();
}

@GetMapping("/{customerId}")
public CustomerDetails getCustomerById(@PathVariable Long customerId) {
	return customerService.getCustomerById(customerId);
}

@PutMapping("/{customerId}")
public CustomerDetails updateCustomerDetails(@PathVariable Long customerId,@Valid @RequestBody CustomerDetails customerDetails) {
	return customerService.updateCustomerDetails(customerId, customerDetails);
}

@DeleteMapping("/{customerId}")
public String deleteCustomerById(@PathVariable Long customerId) {
	return customerService.deleteCustomerById(customerId);
}

}
