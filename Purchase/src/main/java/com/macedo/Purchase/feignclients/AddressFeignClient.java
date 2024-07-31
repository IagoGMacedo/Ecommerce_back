package com.macedo.Purchase.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.macedo.Purchase.dtos.AddressDTO;
import com.macedo.Purchase.entities.Address;

@Component
@FeignClient(name = "customer",path = "/addresses")
public interface AddressFeignClient {
    
    @GetMapping("{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable Integer id);

}
