package com.macedo.Purchase.dtos;

import java.math.BigDecimal;

import com.macedo.Purchase.entities.PaymentMethod;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponsePaymentDTO {
    private Integer id;
    private PaymentMethod paymentMethod;
    private BigDecimal price;
    private ResponsePaymentCreditCardDTO creditCard;
    private ResponsePaymentDebitCardDTO debitCard;
}
