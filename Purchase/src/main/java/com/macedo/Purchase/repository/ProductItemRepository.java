package com.macedo.Purchase.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.macedo.Purchase.entities.ProductItem;

public interface ProductItemRepository extends JpaRepository<ProductItem,Integer> {

}
