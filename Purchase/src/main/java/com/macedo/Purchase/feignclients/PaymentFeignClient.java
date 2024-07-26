package com.macedo.Purchase.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import com.macedo.Purchase.entities.Payment;

@Component
@FeignClient(name = "payment",path = "/payments")
public interface PaymentFeignClient {
    @PostMapping
    public ResponseEntity<Payment> savePayment(Payment payment);
}
