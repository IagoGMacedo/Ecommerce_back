package com.macedo.Customer.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.macedo.Customer.dtos.CustomerDTO;

@RefreshScope
@RestController
@RequestMapping("hello")
public class HelloWorldResource {

    @Value("${saudation.key}")
    private String saudation;

    @GetMapping
    public ResponseEntity<String> getCustomers(CustomerDTO filtro) {
        return ResponseEntity.ok("faz o L " + saudation);
    }

}
