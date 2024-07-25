package com.macedo.Product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.macedo.Product.entities.Product;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    public Optional<List<Product>> findByCategoriesId(Integer categoryId);
}
