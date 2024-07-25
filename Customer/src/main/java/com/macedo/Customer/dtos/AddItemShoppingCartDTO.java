package com.macedo.Customer.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddItemShoppingCartDTO {
    private Integer idShoppingCart;
    private Integer idCustomer;
    private ProductItemDTO productItem;
}
