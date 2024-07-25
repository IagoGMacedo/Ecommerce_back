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

import com.macedo.Purchase.dtos.ShippingTaxDTO;
import com.macedo.Purchase.service.ShippingTaxService;

@RefreshScope
@RestController
@RequestMapping(value = "/shippingTaxes")
public class ShippingTaxResource {
    @Autowired
    private ShippingTaxService shippingTaxService;

    @GetMapping
    public ResponseEntity<List<ShippingTaxDTO>> getShippingTaxs(ShippingTaxDTO filtro) {
            return new ResponseEntity<List<ShippingTaxDTO>>((shippingTaxService.getShippingTaxes(filtro)),
                            HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<ShippingTaxDTO> getShippingTaxById(@PathVariable Integer id) {
            return new ResponseEntity<ShippingTaxDTO>((shippingTaxService.getShippingTaxById(id)),
                            HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ShippingTaxDTO> createShippingTax(
                    @RequestBody ShippingTaxDTO ShippingTax) {
            return new ResponseEntity<ShippingTaxDTO>((shippingTaxService.createShippingTax(ShippingTax)),
                            HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<ShippingTaxDTO> updateShippingTax(@PathVariable Integer id,
                    @RequestBody ShippingTaxDTO ShippingTax) {
            return new ResponseEntity<ShippingTaxDTO>((shippingTaxService.updateShippingTax(id, ShippingTax)),
                            HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<ShippingTaxDTO> patchShippingTax(@PathVariable Integer id,
                    @RequestBody ShippingTaxDTO ShippingTaxIncompletaDTO) {
            return new ResponseEntity<ShippingTaxDTO>(
                            (shippingTaxService.patchShippingTax(id, ShippingTaxIncompletaDTO)),
                            HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteShippingTax(@PathVariable Integer id) {
            shippingTaxService.deleteShippingTax(id);
            return ResponseEntity.ok().build();
    }

}
