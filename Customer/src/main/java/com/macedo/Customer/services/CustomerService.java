package com.macedo.Customer.services;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.macedo.Customer.Exceptions.NotFoundException;
import com.macedo.Customer.dtos.CustomerDTO;
import com.macedo.Customer.entities.Customer;
import com.macedo.Customer.entities.ShoppingCart;
import com.macedo.Customer.repository.CustomerRepository;
import com.macedo.Customer.repository.ShoppingCartRepository;
import com.macedo.Customer.utils.Patcher;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final ShoppingCartRepository shoppingCartRepository;
    private final Patcher patcher;

    public List<CustomerDTO> getCustomers(CustomerDTO filtro) {
        Customer obj = extractCustomer(filtro);
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(obj, matcher);
        return toDTOList(customerRepository.findAll(example));
    }

    public CustomerDTO getCustomerById(Integer id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("customer"));
        return toDTO(customer);
    }

    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Customer newCustomer = new Customer();
        newCustomer = extractCustomer(customerDTO);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCustomer(newCustomer);

        newCustomer.setShoppingCart(shoppingCart);
        customerRepository.save(newCustomer);
        shoppingCartRepository.save(shoppingCart);
        return toDTO(newCustomer);
    }

    public CustomerDTO updateCustomer(Integer id, CustomerDTO customerDTO) {
        Customer existingCustomer = customerRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("customer"));

        Customer newCustomer = extractCustomer(customerDTO);
        newCustomer.setId(existingCustomer.getId());
        return toDTO(customerRepository.save(newCustomer));
    }

    public CustomerDTO patchCustomer(Integer id, CustomerDTO incompleteCustomerDTO) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("customer"));

        Customer incompleteCustomer = extractCustomer(incompleteCustomerDTO);

        patcher.patchPropertiesNotNull(incompleteCustomer, existingCustomer);
        return toDTO(customerRepository.save(existingCustomer));
    }

    public void deleteCustomer(Integer id) {
        Customer customer = customerRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("customer"));
        customerRepository.delete(customer);
    }

    private Customer extractCustomer(CustomerDTO dto) {
        Customer customer = new Customer();
        customer.setName(dto.getName());
        customer.setEmail(dto.getEmail());
        return customer;
    }

    private CustomerDTO toDTO(Customer customer) {
        return CustomerDTO.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .build();
    }

    private List<CustomerDTO> toDTOList(List<Customer> customers) {
        if (CollectionUtils.isEmpty(customers)) {
            return Collections.emptyList();
        }
        return customers.stream().map(
                customer -> CustomerDTO
                        .builder()
                        .id(customer.getId())
                        .name(customer.getName())
                        .email(customer.getEmail())
                        .build())
                .collect(Collectors.toList());
    }

}
