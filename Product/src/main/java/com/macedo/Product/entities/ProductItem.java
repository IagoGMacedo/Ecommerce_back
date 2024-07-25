package com.macedo.Product.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ProductItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column
    private int quantity;

    @Column
    private BigDecimal subTotal;

    @ManyToOne
    @JoinColumn(name = "shoppingCart_id") // produto está no carrinho
    private ShoppingCart shoppingCart;

    @ManyToOne
    @JoinColumn(name = "purchase_id") // produto foi comprado
    private Purchase purchase;


    public BigDecimal getTotalItemPrice() {
        return product.getPrice().multiply(new BigDecimal(quantity));
    }
}
