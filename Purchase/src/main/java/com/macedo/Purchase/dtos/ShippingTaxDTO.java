package com.macedo.Purchase.dtos;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShippingTaxDTO {
    private Integer id;
    private String state;
    private BigDecimal value;
}
