package com.macedo.Customer.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.macedo.Customer.entities.Product;

@Component
@FeignClient(name = "product",path = "/products")
public interface ProductFeignClient {
    @GetMapping("{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer id);

    @PostMapping("/save")
    public ResponseEntity<Product> saveProduct(@RequestBody Product product);
}
