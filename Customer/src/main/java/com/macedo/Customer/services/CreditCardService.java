package com.macedo.Customer.services;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.macedo.Customer.Exceptions.NotFoundException;
import com.macedo.Customer.dtos.RegisterCreditCardDTO;
import com.macedo.Customer.dtos.ResponseCreditCardDTO;
import com.macedo.Customer.entities.CreditCard;
import com.macedo.Customer.entities.Customer;
import com.macedo.Customer.repository.CreditCardRepository;
import com.macedo.Customer.repository.CustomerRepository;
import com.macedo.Customer.utils.Patcher;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreditCardService {

    private final CreditCardRepository creditCardRepository;
    private final CustomerRepository customerRepository;
    private final Patcher patcher;

    public List<ResponseCreditCardDTO> getCreditCards(RegisterCreditCardDTO filtro) {
        CreditCard obj = extractCreditCard(filtro);
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(obj, matcher);
        return toDTOList(creditCardRepository.findAll(example));
    }

    public ResponseCreditCardDTO getCreditCardById(Integer id) {
        CreditCard creditCard = creditCardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("credit card"));
        return toDTO(creditCard);
    }

    public List<ResponseCreditCardDTO> getCreditCardsByCustomerId(Integer customerId) {
        Customer customer = customerRepository
                .findById(customerId)
                .orElseThrow(() -> new NotFoundException("customer"));

        List<CreditCard> list = creditCardRepository.findByCustomerId(customerId);

        return toDTOList(list);
    }

    public ResponseCreditCardDTO createCreditCard(RegisterCreditCardDTO creditCard) {
        Integer idCustomer = creditCard.getIdCustomer();
        Customer customer = customerRepository
                .findById(idCustomer)
                .orElseThrow(() -> new NotFoundException("customer"));

        CreditCard newCreditCard = new CreditCard();
        newCreditCard = extractCreditCard(creditCard);
        newCreditCard.setCustomer(customer);
        return toDTO(creditCardRepository.save(newCreditCard));
    }

    public ResponseCreditCardDTO updateCreditCard(Integer id, RegisterCreditCardDTO creditCard) {
        CreditCard existingCreditCard = creditCardRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("creditCard"));

        CreditCard newCreditCard = extractCreditCard(creditCard);
        newCreditCard.setId(existingCreditCard.getId());
        return toDTO(creditCardRepository.save(newCreditCard));
    }

    public ResponseCreditCardDTO patchCreditCard(Integer id, RegisterCreditCardDTO CreditCardIncompletaDto) {
        CreditCard existingCreditCard = creditCardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("creditCard"));

        CreditCard incompleteCreditCard = extractCreditCard(CreditCardIncompletaDto);

        patcher.patchPropertiesNotNull(incompleteCreditCard, existingCreditCard);
        return toDTO(creditCardRepository.save(existingCreditCard));
    }

    public void deleteCreditCard(Integer id) {
        CreditCard creditCard = creditCardRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("creditCard"));
        creditCardRepository.delete(creditCard);
    }

    private CreditCard extractCreditCard(RegisterCreditCardDTO dto) {
        CreditCard creditCard = new CreditCard();
        if (dto.getIdCustomer() != null) {
            Integer idCustomer = dto.getIdCustomer();
            Customer customer = customerRepository
                    .findById(idCustomer)
                    .orElseThrow(() -> new NotFoundException("customer"));
            creditCard.setCustomer(customer);
        }
        creditCard.setCardHolderName(dto.getCardHolderName());
        creditCard.setNumber(dto.getNumber());
        creditCard.setValidity(dto.getValidity());
        creditCard.setCvv(dto.getCvv());
        return creditCard;
    }

    private ResponseCreditCardDTO toDTO(CreditCard creditCard) {
        return ResponseCreditCardDTO
                .builder()
                .id(creditCard.getId())
                .idCustomer(creditCard.getCustomer().getId())
                .cardHolderName(creditCard.getCardHolderName())
                .lastNumbers(creditCard.getNumber().substring(creditCard.getNumber().length() - 4))
                .build();
    }

    private List<ResponseCreditCardDTO> toDTOList(List<CreditCard> creditCards) {
        if (CollectionUtils.isEmpty(creditCards)) {
            return Collections.emptyList();
        }
        return creditCards.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

}
