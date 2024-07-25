package com.macedo.Payment.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponsePaymentCreditCardDTO {
    private Integer idCreditCard;
    private Integer installments;
}
