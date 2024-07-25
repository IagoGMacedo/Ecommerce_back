package com.macedo.Purchase.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.macedo.Purchase.dtos.ShippingTaxDTO;
import com.macedo.Purchase.entities.ShippingTax;
import com.macedo.Purchase.exceptions.NotFoundException;
import com.macedo.Purchase.repository.ShippingTaxRepository;
import com.macedo.Purchase.utils.Patcher;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShippingTaxService {
    private final ShippingTaxRepository shippingTaxRepository;
    private final CustomerRepository customerRepository;
    private final Patcher patcher;

    public List<ShippingTaxDTO> getShippingTaxes(ShippingTaxDTO filtro) {
        ShippingTax obj = extractShippingTax(filtro);
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(obj, matcher);
        return toDTOList(shippingTaxRepository.findAll(example));
    }

    public ShippingTaxDTO getShippingTaxById(Integer id) {
        ShippingTax shippingTax = shippingTaxRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("shipping tax"));
        return toDTO(shippingTax);
    }

    public ShippingTaxDTO createShippingTax(ShippingTaxDTO shippingTax) {
        ShippingTax newShippingTax = new ShippingTax();
        newShippingTax = extractShippingTax(shippingTax);
        return toDTO(shippingTaxRepository.save(newShippingTax));
    }

    public ShippingTaxDTO updateShippingTax(Integer id, ShippingTaxDTO shippingTax) {
        ShippingTax existingShippingTax = shippingTaxRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("shipping Tax"));

        ShippingTax newShippingTax = extractShippingTax(shippingTax);
        newShippingTax.setId(existingShippingTax.getId());
        return toDTO(shippingTaxRepository.save(newShippingTax));
    }

    public ShippingTaxDTO patchShippingTax(Integer id, ShippingTaxDTO ShippingTaxIncompletaDto) {
        ShippingTax existingShippingTax = shippingTaxRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("shippingTax"));

        ShippingTax incompleteShippingTax = extractShippingTax(ShippingTaxIncompletaDto);

        patcher.patchPropertiesNotNull(incompleteShippingTax, existingShippingTax);
        return toDTO(shippingTaxRepository.save(existingShippingTax));
    }

    public void deleteShippingTax(Integer id) {
        ShippingTax shippingTax = shippingTaxRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("shippingTax"));
        shippingTaxRepository.delete(shippingTax);
    }

    private ShippingTax extractShippingTax(ShippingTaxDTO dto) {
        ShippingTax shippingTax = new ShippingTax();
        shippingTax.setState(dto.getState());
        shippingTax.setValue(dto.getValue());
        return shippingTax;
    }

    private ShippingTaxDTO toDTO(ShippingTax shippingTax) {
        return ShippingTaxDTO
                .builder()
                .id(shippingTax.getId())
                .state(shippingTax.getState())
                .value(shippingTax.getValue())
                .build();
    }

    private List<ShippingTaxDTO> toDTOList(List<ShippingTax> shippingTaxs) {
        if (CollectionUtils.isEmpty(shippingTaxs)) {
            return Collections.emptyList();
        }
        return shippingTaxs.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
