package com.macedo.Purchase.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.macedo.Purchase.dtos.RegisterPurchaseDTO;
import com.macedo.Purchase.dtos.ResponsePurchaseDTO;
import com.macedo.Purchase.service.PurchaseService;

@RefreshScope
@RestController
@RequestMapping(value = "/purchases")
public class PurchaseResource {
    @Autowired
    private PurchaseService purchaseService;

    @GetMapping
    public ResponseEntity<List<ResponsePurchaseDTO>> getPurchases() {
        return new ResponseEntity<List<ResponsePurchaseDTO>>((purchaseService.getPurchases()), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponsePurchaseDTO> getPurchaseById(@PathVariable Integer id) {
        return new ResponseEntity<ResponsePurchaseDTO>((purchaseService.getPurchaseById(id)), HttpStatus.OK);
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<List<ResponsePurchaseDTO>> getPurchasesByCustomerId(@PathVariable Integer id) {
        return new ResponseEntity<List<ResponsePurchaseDTO>>((purchaseService.getPurchasesByCustomerId(id)),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponsePurchaseDTO> createPurchase(@RequestBody RegisterPurchaseDTO Purchase) {
        return new ResponseEntity<ResponsePurchaseDTO>((purchaseService.createPurchase(Purchase)), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletePurchase(@PathVariable Integer id) {
        purchaseService.deletePurchase(id);
        return ResponseEntity.ok().build();
    }

}
