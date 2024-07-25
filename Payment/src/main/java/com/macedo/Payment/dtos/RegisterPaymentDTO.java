package com.macedo.Payment.dtos;

import com.macedo.Payment.entities.PaymentMethod;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterPaymentDTO {
    private PaymentMethod paymentMethod;
    private Integer idCreditCard; //armazena se o pagamento tiver sido como cartão de credito;
    private Integer installments; //quantidade de parcelas de um pagamento
    private Integer idPurchase;
}
