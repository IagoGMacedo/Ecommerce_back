package com.macedo.Product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.macedo.Product.entities.Category;

public interface CategoryRepository extends JpaRepository<Category,Integer> {

}
