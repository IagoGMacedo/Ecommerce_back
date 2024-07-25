package com.macedo.Customer.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterCreditCardDTO {
    private Integer idCustomer;
    private String cardHolderName;
    private String validity;
    private String number;
    private String cvv;
    private String lastNumbers;
}
