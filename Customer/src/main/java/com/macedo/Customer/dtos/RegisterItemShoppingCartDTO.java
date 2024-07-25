package com.macedo.Customer.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterItemShoppingCartDTO {
    private Integer idShoppingCart;
    private Integer idProductItem;
    private Integer quantity;
}
