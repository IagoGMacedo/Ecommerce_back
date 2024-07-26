package com.macedo.Purchase.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.macedo.Purchase.entities.Customer;

@Component
@FeignClient(name = "customer",path = "/customers")
public interface CustomerFeignClient {

    @GetMapping("{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Integer id);

}
