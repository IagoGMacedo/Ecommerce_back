package com.macedo.Customer.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.macedo.Customer.dtos.AddItemShoppingCartDTO;
import com.macedo.Customer.dtos.RegisterItemShoppingCartDTO;
import com.macedo.Customer.dtos.ShoppingCartDTO;
import com.macedo.Customer.services.ShoppingCartService;

@RefreshScope
@RestController
@RequestMapping(value = "/shoppingCart")
public class ShoppingCartResource {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping
    public ResponseEntity<List<ShoppingCartDTO>> getShoppingCarts() {
        return new ResponseEntity<List<ShoppingCartDTO>>((shoppingCartService.getShoppingCarts()), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<ShoppingCartDTO> getShoppingCartById(@PathVariable Integer id) {
        return new ResponseEntity<ShoppingCartDTO>((shoppingCartService.getShoppingCartById(id)), HttpStatus.OK);
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<ShoppingCartDTO> getShoppingCartByCustomerId(@PathVariable Integer id) {
        return new ResponseEntity<ShoppingCartDTO>((shoppingCartService.getShoppingCartByCustomerId(id)), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ShoppingCartDTO> addItemToCart(@RequestBody AddItemShoppingCartDTO addProductItem) {
        return new ResponseEntity<ShoppingCartDTO>((shoppingCartService.addItemToCart(addProductItem)),
                HttpStatus.CREATED);
    }

    @PatchMapping()
    public ResponseEntity<ShoppingCartDTO> updateItemQuantity(@RequestBody RegisterItemShoppingCartDTO dto) {
        return new ResponseEntity<ShoppingCartDTO>((shoppingCartService.updateItemQuantity(dto)), HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteItemFromCart(@RequestBody RegisterItemShoppingCartDTO deleteProductItem) {
        shoppingCartService.deleteItemFromCart(deleteProductItem);
        return ResponseEntity.ok().build();
    }


}
