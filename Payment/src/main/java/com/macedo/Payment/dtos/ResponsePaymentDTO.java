package com.macedo.Payment.dtos;

import java.math.BigDecimal;

import com.macedo.Payment.entities.PaymentMethod;

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
