package com.macedo.Customer.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseCreditCardDTO {
    private Integer id;
    private Integer idCustomer;
    private String cardHolderName;
    private String lastNumbers;
}
