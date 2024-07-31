package com.macedo.Purchase.repository;

import java.util.Optional;

import com.macedo.Purchase.entities.ShippingTax;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ShippingTaxRepository extends JpaRepository<ShippingTax,Integer>{

    Optional<ShippingTax> findByState(String state);

}
