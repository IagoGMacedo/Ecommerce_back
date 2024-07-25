package com.macedo.Purchase.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.macedo.Purchase.entities.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase,Integer> {
    List<Purchase> findByCustomerId(Integer customerId);
}
