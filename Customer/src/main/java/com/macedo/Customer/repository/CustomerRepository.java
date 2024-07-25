package com.macedo.Customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.macedo.Customer.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    Customer findByEmail(String email);
}
