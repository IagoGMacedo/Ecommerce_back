package com.macedo.Customer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.macedo.Customer.entities.ShoppingCart;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,Integer> {
    Optional<ShoppingCart> findByCustomerId(Integer customerId);
}
