package com.macedo.Customer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.macedo.Customer.entities.CreditCard;

public interface CreditCardRepository extends JpaRepository<CreditCard, Integer>  {

    List<CreditCard> findByCustomerId(Integer customerId);

}
