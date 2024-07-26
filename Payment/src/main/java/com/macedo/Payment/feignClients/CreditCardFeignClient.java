package com.macedo.Payment.feignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.macedo.Payment.entities.CreditCard;

@Component
@FeignClient(name = "creditCard",path = "/creditCards")
public interface CreditCardFeignClient {
    @GetMapping("{id}")
    public ResponseEntity<CreditCard> getCreditCardById(@PathVariable Integer id);
}
