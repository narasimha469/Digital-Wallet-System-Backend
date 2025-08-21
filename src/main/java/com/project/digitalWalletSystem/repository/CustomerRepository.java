package com.project.digitalWalletSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.digitalWalletSystem.model.CustomerDetails;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerDetails, Long> {

    boolean existsByCustomerEmail(String customerEmail);

    boolean existsByCustomerPhoneNumber(String customerPhoneNumber);

    CustomerDetails findByCustomerEmail(String email);
}
