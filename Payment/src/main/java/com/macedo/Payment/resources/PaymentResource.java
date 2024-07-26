package com.macedo.Payment.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.macedo.Payment.dtos.RegisterPaymentDTO;
import com.macedo.Payment.dtos.ResponsePaymentDTO;
import com.macedo.Payment.entities.Payment;
import com.macedo.Payment.services.PaymentService;

@RefreshScope
@RestController
@RequestMapping(value = "/payments")
public class PaymentResource {
    @Autowired
    private PaymentService paymentService;

    @GetMapping
    public ResponseEntity<List<ResponsePaymentDTO>> getPayments(RegisterPaymentDTO filtro) {
        return new ResponseEntity<List<ResponsePaymentDTO>>((paymentService.getPayments(filtro)), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponsePaymentDTO> getPaymentById(@PathVariable Integer id) {
        return new ResponseEntity<ResponsePaymentDTO>((paymentService.getPaymentById(id)), HttpStatus.OK);
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<List<ResponsePaymentDTO>> getPaymentsByCustomerId(@PathVariable Integer id) {
        return new ResponseEntity<List<ResponsePaymentDTO>>((paymentService.getPaymentsByCustomerId(id)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Payment> savePayment(Payment payment){
        return new ResponseEntity<Payment>((paymentService.savePayment(payment)), HttpStatus.OK);
        
    }



}
