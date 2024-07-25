package com.macedo.Purchase.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.macedo.Purchase.entities.Discount;

public interface DiscountRepository extends JpaRepository<Discount,Integer> {

}
