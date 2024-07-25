package com.macedo.Purchase.resources;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.macedo.Purchase.dtos.DiscountDTO;
import com.macedo.Purchase.service.DiscountService;

@RefreshScope
@RestController
@RequestMapping(value = "/discounts")
public class DiscountResource {
    @Autowired
    private DiscountService discountService;

    @GetMapping
    public ResponseEntity<List<DiscountDTO>> getDiscounts(DiscountDTO filtro) {
        return new ResponseEntity<List<DiscountDTO>>((discountService.getDiscounts(filtro)),
                        HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<DiscountDTO> getDiscountById(@PathVariable Integer id) {
            return new ResponseEntity<DiscountDTO>((discountService.getDiscountById(id)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DiscountDTO> createDiscount(@RequestBody DiscountDTO Discount) {
            return new ResponseEntity<DiscountDTO>((discountService.createDiscount(Discount)),
                            HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<DiscountDTO> updateDiscount(@PathVariable Integer id,
                    @RequestBody DiscountDTO Discount) {
            return new ResponseEntity<DiscountDTO>((discountService.updateDiscount(id, Discount)),
                            HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<DiscountDTO> patchDiscount(@PathVariable Integer id,
                    @RequestBody DiscountDTO DiscountIncompletaDTO) {
            return new ResponseEntity<DiscountDTO>(
                            (discountService.patchDiscount(id, DiscountIncompletaDTO)),
                            HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteDiscount(@PathVariable Integer id) {
            discountService.deleteDiscount(id);
            return ResponseEntity.ok().build();
    }

}
