package com.macedo.Customer.services;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.macedo.Customer.Exceptions.NotFoundException;
import com.macedo.Customer.dtos.AddressDTO;
import com.macedo.Customer.entities.Address;
import com.macedo.Customer.entities.Customer;
import com.macedo.Customer.repository.AddressRepository;
import com.macedo.Customer.repository.CustomerRepository;
import com.macedo.Customer.utils.Patcher;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;

    private final Patcher patcher;

    public List<AddressDTO> getAddresses(AddressDTO filtro) {
        Address obj = extractAddress(filtro);
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(obj, matcher);
        return toDTOList(addressRepository.findAll(example));
    }

    public Address getAddressById(Integer id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("address"));
        return address;
    }

    public List<AddressDTO> getAddressesByCustomerId(Integer customerId) {
        Customer customer = customerRepository
                .findById(customerId)
                .orElseThrow(() -> new NotFoundException("customer"));

        List<Address> list = addressRepository.findByCustomerId(customerId);

        return toDTOList(list);
    }

    public AddressDTO createAddress(AddressDTO address) {
        Integer idCustomer = address.getIdCustomer();
        Customer customer = customerRepository
                .findById(idCustomer)
                .orElseThrow(() -> new NotFoundException("customer"));

        Address newAddress = new Address();
        newAddress = extractAddress(address);
        newAddress.setCustomer(customer);
        return toDTO(addressRepository.save(newAddress));
    }

    public AddressDTO updateAddress(Integer id, AddressDTO Address) {
        Address existingAddress = addressRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("address"));

        Address newAddress = extractAddress(Address);
        newAddress.setId(existingAddress.getId());
        return toDTO(addressRepository.save(newAddress));
    }

    public AddressDTO patchAddress(Integer id, AddressDTO AddressIncompletaDto) {
        Address existingAddress = addressRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("address"));

        Address incompleteAddress = extractAddress(AddressIncompletaDto);

        patcher.patchPropertiesNotNull(incompleteAddress, existingAddress);
        return toDTO(addressRepository.save(existingAddress));
    }

    public void deleteAddress(Integer id) {
        Address address = addressRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("address"));
        addressRepository.delete(address);
    }

    private Address extractAddress(AddressDTO dto) {
        Address address = new Address();
        if (dto.getIdCustomer() != null) {
            Integer idCustomer = dto.getIdCustomer();
            Customer customer = customerRepository
                    .findById(idCustomer)
                    .orElseThrow(() -> new NotFoundException("customer"));
            address.setCustomer(customer);
        }
        address.setCompleteAddress(dto.getCompleteAddress());
        address.setCep(dto.getCep());
        address.setCity(dto.getCity());
        address.setDistrict(dto.getDistrict());
        address.setComplement(dto.getComplement());
        address.setNumber(dto.getNumber());
        address.setState(dto.getState());
        return address;
    }

    private AddressDTO toDTO(Address address) {
        return AddressDTO.builder()
                .id(address.getId())
                .idCustomer(address.getCustomer().getId())
                .cep(address.getCep())
                .completeAddress(address.getCompleteAddress())
                .number(address.getNumber())
                .complement(address.getComplement())
                .district(address.getDistrict())
                .city(address.getCity())
                .state(address.getState())
                .build();
    }

    private List<AddressDTO> toDTOList(List<Address> addresses) {
        if (CollectionUtils.isEmpty(addresses)) {
            return Collections.emptyList();
        }
        return addresses.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

}
