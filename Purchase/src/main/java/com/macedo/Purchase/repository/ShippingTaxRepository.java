package com.macedo.Purchase.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.macedo.Purchase.entities.ShippingTax;

public interface ShippingTaxRepository extends JpaRepository<ShippingTax,Integer> {
    Optional<ShippingTax> findByState(String state);
}
