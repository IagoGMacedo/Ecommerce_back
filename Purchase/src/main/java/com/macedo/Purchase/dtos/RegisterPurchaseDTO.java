package com.macedo.Purchase.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterPurchaseDTO {
    private Integer idCustomer;
    private List<ProductItemDTO> productItems;
    private RegisterPaymentDTO payment;
    private Integer idAddress;
    private Integer idDiscount;
}
