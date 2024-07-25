package com.macedo.Purchase.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.macedo.Purchase.dtos.DiscountDTO;
import com.macedo.Purchase.entities.Discount;
import com.macedo.Purchase.exceptions.NotFoundException;
import com.macedo.Purchase.repository.DiscountRepository;
import com.macedo.Purchase.utils.Patcher;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DiscountService {

    private final DiscountRepository discountRepository;
    private final Patcher patcher;

    public List<DiscountDTO> getDiscounts(DiscountDTO filtro) {
        Discount obj = extractDiscount(filtro);
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(obj, matcher);
        return toDTOList(discountRepository.findAll(example));
    }

    public DiscountDTO getDiscountById(Integer id) {
        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("discount"));
        return toDTO(discount);
    }

    public DiscountDTO createDiscount(DiscountDTO discount) {
        Discount newDiscount = new Discount();
        newDiscount = extractDiscount(discount);
        return toDTO(discountRepository.save(newDiscount));
    }

    public DiscountDTO updateDiscount(Integer id, DiscountDTO discount) {
        Discount existingDiscount = discountRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("discount"));

        Discount newDiscount = extractDiscount(discount);
        newDiscount.setId(existingDiscount.getId());
        return toDTO(discountRepository.save(newDiscount));
    }

    public DiscountDTO patchDiscount(Integer id, DiscountDTO DiscountIncompletaDto) {
        Discount existingDiscount = discountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("discount"));

        Discount incompleteDiscount = extractDiscount(DiscountIncompletaDto);

        patcher.patchPropertiesNotNull(incompleteDiscount, existingDiscount);
        return toDTO(discountRepository.save(existingDiscount));
    }

    public void deleteDiscount(Integer id) {
        Discount discount = discountRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("discount"));
        discountRepository.delete(discount);
    }

    private Discount extractDiscount(DiscountDTO dto) {
        Discount discount = new Discount();
        discount.setRate(dto.getRate());
        return discount;
    }

    private DiscountDTO toDTO(Discount discount) {
        return DiscountDTO
                .builder()
                .id(discount.getId())
                .rate(discount.getRate())
                .build();
    }

    private List<DiscountDTO> toDTOList(List<Discount> creditCards) {
        if (CollectionUtils.isEmpty(creditCards)) {
            return Collections.emptyList();
        }
        return creditCards.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

}
