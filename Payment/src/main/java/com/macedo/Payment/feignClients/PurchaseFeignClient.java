package com.macedo.Payment.feignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Component
@FeignClient(name = "purchase",path = "/purchases")
public interface PurchaseFeignClient {

}
