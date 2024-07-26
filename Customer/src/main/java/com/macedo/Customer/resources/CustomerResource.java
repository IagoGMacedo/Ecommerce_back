package com.macedo.Customer.resources;

import org.slf4j.LoggerFactory;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.macedo.Customer.dtos.CustomerDTO;
import com.macedo.Customer.entities.Customer;
import com.macedo.Customer.services.CustomerService;

@RefreshScope
@RestController
@RequestMapping(value = "/customers")
public class CustomerResource {

    private static Logger logger = LoggerFactory.getLogger(CustomerResource.class);

    @Autowired
    CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getCustomers(CustomerDTO filtro) {
        return new ResponseEntity<List<CustomerDTO>>((customerService.getCustomers(filtro)), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Integer id) {
        return new ResponseEntity<Customer>((customerService.getCustomerById(id)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO Customer) {
        return new ResponseEntity<CustomerDTO>((customerService.createCustomer(Customer)), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Integer id,
            @RequestBody CustomerDTO Customer) {
        return new ResponseEntity<CustomerDTO>((customerService.updateCustomer(id, Customer)), HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<CustomerDTO> patchCustomer(@PathVariable Integer id,
            @RequestBody CustomerDTO CustomerIncompletaDTO) {
        return new ResponseEntity<CustomerDTO>((customerService.patchCustomer(id, CustomerIncompletaDTO)),
                HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Integer id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok().build();
    }


}
