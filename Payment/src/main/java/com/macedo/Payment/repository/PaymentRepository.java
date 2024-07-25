package com.macedo.Payment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.macedo.Payment.entities.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    @Query(value="SELECT p FROM Payment p JOIN p.purchase pu where pu.customer.id = :customerId")
    Optional<List<Payment>> findByCustomerId(@Param("customerId") Integer customerId);

}
