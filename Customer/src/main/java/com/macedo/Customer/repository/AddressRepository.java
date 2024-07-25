package com.macedo.Customer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.macedo.Customer.entities.Address;

public interface AddressRepository extends JpaRepository<Address,Integer> {
    List<Address> findByCustomerId(Integer customerId);
}
