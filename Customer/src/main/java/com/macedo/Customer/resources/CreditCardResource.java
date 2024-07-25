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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.macedo.Customer.dtos.RegisterCreditCardDTO;
import com.macedo.Customer.dtos.ResponseCreditCardDTO;
import com.macedo.Customer.services.CreditCardService;

@RefreshScope
@RestController
@RequestMapping(value = "/creditcards")
public class CreditCardResource {
    @Autowired
    private CreditCardService creditCardService;

    @GetMapping
        public ResponseEntity<List<ResponseCreditCardDTO>> getCreditCards(RegisterCreditCardDTO filtro) {
                return new ResponseEntity<List<ResponseCreditCardDTO>>((creditCardService.getCreditCards(filtro)),
                                HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponseCreditCardDTO> getCreditCardById(@PathVariable Integer id) {
            return new ResponseEntity<ResponseCreditCardDTO>((creditCardService.getCreditCardById(id)),
                            HttpStatus.OK);
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<List<ResponseCreditCardDTO>> getCreditCardsByCustomerId(@PathVariable Integer id) {
            return new ResponseEntity<List<ResponseCreditCardDTO>>(
                            (creditCardService.getCreditCardsByCustomerId(id)),
                            HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseCreditCardDTO> createCreditCard(
                    @RequestBody RegisterCreditCardDTO CreditCard) {
            return new ResponseEntity<ResponseCreditCardDTO>((creditCardService.createCreditCard(CreditCard)),
                            HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<ResponseCreditCardDTO> updateCreditCard(@PathVariable Integer id,
                    @RequestBody RegisterCreditCardDTO CreditCard) {
            return new ResponseEntity<ResponseCreditCardDTO>((creditCardService.updateCreditCard(id, CreditCard)),
                            HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<ResponseCreditCardDTO> patchCreditCard(@PathVariable Integer id,
                    @RequestBody RegisterCreditCardDTO CreditCardIncompletaDTO) {
            return new ResponseEntity<ResponseCreditCardDTO>(
                            (creditCardService.patchCreditCard(id, CreditCardIncompletaDTO)),
                            HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCreditCard(@PathVariable Integer id) {
            creditCardService.deleteCreditCard(id);
            return ResponseEntity.ok().build();
    }

    

}
