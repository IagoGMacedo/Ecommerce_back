package com.macedo.Customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.macedo.Customer.entities.ProductItem;

public interface ProductItemRepository extends JpaRepository<ProductItem,Integer> {

}
