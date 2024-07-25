package com.macedo.Customer.dtos;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShoppingCartDTO {
    private Integer id;
    private Integer idCustomer;
    private List<ProductItemDTO> productItems;
    private BigDecimal totalPrice;
}
