package com.macedo.Payment.services;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.macedo.Payment.Exceptions.NotFoundException;
import com.macedo.Payment.dtos.RegisterPaymentDTO;
import com.macedo.Payment.dtos.ResponsePaymentCreditCardDTO;
import com.macedo.Payment.dtos.ResponsePaymentDTO;
import com.macedo.Payment.dtos.ResponsePaymentDebitCardDTO;
import com.macedo.Payment.entities.CreditCard;
import com.macedo.Payment.entities.Customer;
import com.macedo.Payment.entities.Payment;
import com.macedo.Payment.entities.PaymentMethod;
import com.macedo.Payment.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final PurchaseRepository purchaseRepository;

    private final CustomerRepository customerRepository;

    private final CreditCardRepository creditCardRepository;

    public List<ResponsePaymentDTO> getPayments(RegisterPaymentDTO filtro) {
        Payment obj = extractPayment(filtro);
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(obj, matcher);
        return toDTOList(paymentRepository.findAll(example));
    }

    public ResponsePaymentDTO getPaymentById(Integer id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("payment"));
        return toDTO(payment);
    }

    public List<ResponsePaymentDTO> getPaymentsByCustomerId(Integer customerId) {
        Customer customer = customerRepository
                .findById(customerId)
                .orElseThrow(() -> new NotFoundException("customer"));

        List<Payment> list = paymentRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new NotFoundException("customer"));

        return toDTOList(list);
    }

    private Payment extractPayment(RegisterPaymentDTO dto) {
        Payment payment = new Payment();
        payment.setPaymentMethod(dto.getPaymentMethod());
        if (payment.getPaymentMethod() == PaymentMethod.CARTAO_CREDITO
                || payment.getPaymentMethod() == PaymentMethod.CARTAO_DEBITO) {
            Integer idCreditCard = dto.getIdCreditCard();
            CreditCard creditCard = creditCardRepository
                    .findById(idCreditCard)
                    .orElseThrow(() -> new NotFoundException("credit card"));
            payment.setCreditCard(creditCard);

            if (payment.getPaymentMethod() == PaymentMethod.CARTAO_CREDITO)
                payment.setInstallments(dto.getInstallments());
        }
        return payment;
    }

    private ResponsePaymentDTO toDTO(Payment payment) {
        ResponsePaymentDTO response = ResponsePaymentDTO
                .builder()
                .id(payment.getId())
                .paymentMethod(payment.getPaymentMethod())
                .price(payment.getPrice())
                .build();

        if (payment.getPaymentMethod() == PaymentMethod.CARTAO_CREDITO)
            response.setCreditCard(toCreditCardDTO(payment));

        if (payment.getPaymentMethod() == PaymentMethod.CARTAO_DEBITO)
            response.setDebitCard(toDebitCardDTO(payment));

        return response;
    }

    private List<ResponsePaymentDTO> toDTOList(List<Payment> payments) {
        if (CollectionUtils.isEmpty(payments)) {
            return Collections.emptyList();
        }
        return payments.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private ResponsePaymentCreditCardDTO toCreditCardDTO(Payment payment) {
        return ResponsePaymentCreditCardDTO
                .builder()
                .idCreditCard(payment.getCreditCard().getId())
                .installments(payment.getInstallments())
                .build();
    }

    private ResponsePaymentDebitCardDTO toDebitCardDTO(Payment payment) {
        return ResponsePaymentDebitCardDTO
                .builder()
                .idCreditCard(payment.getCreditCard().getId())
                .build();
    }

}
