package com.macedo.Purchase.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.macedo.Purchase.dtos.ProductItemDTO;
import com.macedo.Purchase.dtos.RegisterPaymentDTO;
import com.macedo.Purchase.dtos.RegisterPurchaseDTO;
import com.macedo.Purchase.dtos.ResponsePaymentCreditCardDTO;
import com.macedo.Purchase.dtos.ResponsePaymentDTO;
import com.macedo.Purchase.dtos.ResponsePaymentDebitCardDTO;
import com.macedo.Purchase.dtos.ResponsePurchaseDTO;
import com.macedo.Purchase.entities.Address;
import com.macedo.Purchase.entities.CreditCard;
import com.macedo.Purchase.entities.Customer;
import com.macedo.Purchase.entities.Discount;
import com.macedo.Purchase.entities.Payment;
import com.macedo.Purchase.entities.PaymentMethod;
import com.macedo.Purchase.entities.Product;
import com.macedo.Purchase.entities.ProductItem;
import com.macedo.Purchase.entities.Purchase;
import com.macedo.Purchase.entities.ShippingTax;
import com.macedo.Purchase.exceptions.BusinessRuleException;
import com.macedo.Purchase.exceptions.NotFoundException;
import com.macedo.Purchase.repository.DiscountRepository;
import com.macedo.Purchase.repository.PurchaseRepository;
import com.macedo.Purchase.repository.ShippingTaxRepository;
import com.macedo.Purchase.utils.Patcher;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;

    private final CustomerRepository customerRepository;

    private final AddressRepository addressRepository;

    private final ProductRepository productRepository;

    private final ProductItemRepository productItemRepository;

    private final PaymentRepository paymentRepository;

    private final CreditCardRepository creditCardRepository;

    private final DiscountRepository discountRepository;

    private final ShippingTaxRepository shippingTaxRepository;

    private final Patcher patcher;

    public List<ResponsePurchaseDTO> getPurchases() {
        return toDTOList(purchaseRepository.findAll());
    }

    public ResponsePurchaseDTO getPurchaseById(Integer id) {
        Purchase purchase = purchaseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("purchase"));
        return toDTO(purchase);
    }

    public List<ResponsePurchaseDTO> getPurchasesByCustomerId(Integer customerId) {
        Customer customer = customerRepository
                .findById(customerId)
                .orElseThrow(() -> new NotFoundException("customer"));

        List<Purchase> list = purchaseRepository.findByCustomerId(customerId);

        return toDTOList(list);
    }

    public ResponsePurchaseDTO createPurchase(RegisterPurchaseDTO purchase) {
        Integer idCustomer = purchase.getIdCustomer();
        Customer customer = customerRepository
                .findById(idCustomer)
                .orElseThrow(() -> new NotFoundException("customer"));

        Integer idAddress = purchase.getIdAddress();
        Address address = addressRepository
                .findById(idAddress)
                .orElseThrow(() -> new NotFoundException("address"));

        BigDecimal shippingTax = BigDecimal.ZERO;

        Optional<ShippingTax> optShippingTax = shippingTaxRepository.findByState(address.getState());
        if (optShippingTax.isPresent()) {
            shippingTax = optShippingTax.get().getValue();
        }

        Integer idDiscount = purchase.getIdDiscount();
        Discount discount = null;
        if (idDiscount != null) {
            discount = discountRepository
                    .findById(idDiscount)
                    .orElseThrow(() -> new NotFoundException("discount"));
        }

        Purchase newPurchase = new Purchase();
        newPurchase.setDate(LocalDate.now());
        newPurchase.setCustomer(customer);
        newPurchase.setAddress(address);
        newPurchase.setDiscount(discount);
        newPurchase.setShippingTax(shippingTax);

        List<ProductItem> productItems = extractProductItems(newPurchase, purchase.getProductItems());
        BigDecimal totalPrice = getTotalPrice(productItems).add(shippingTax);
        if (discount != null)
            totalPrice = calculateDescount(totalPrice, discount);

        Payment payment = extractPayment(newPurchase, purchase.getPayment(), totalPrice);

        newPurchase.setTotalPrice(totalPrice);
        newPurchase.setPayment(payment);

        purchaseRepository.save(newPurchase);
        productItemRepository.saveAll(productItems);
        paymentRepository.save(payment);
        newPurchase.setProductItems(productItems);

        return toDTO(newPurchase);
    }

    public void deletePurchase(Integer id) {
        Purchase purchase = purchaseRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("purchase"));
        purchaseRepository.delete(purchase);
    }

    private List<ProductItem> extractProductItems(Purchase newPurchase, List<ProductItemDTO> productItems) {
        if (productItems.isEmpty()) {
            throw new BusinessRuleException("Não é possível realizar um pedido sem items.");
        }
        return productItems
                .stream()
                .map(dto -> {
                    Integer idProduct = dto.getIdProduct();
                    Product product = productRepository
                            .findById(idProduct)
                            .orElseThrow(() -> new NotFoundException("product"));

                    if (product.getStockQuantity() < dto.getQuantity()) {
                        throw new BusinessRuleException("Não existe a quantidade solicitada no estoque");
                    }
                    product.setStockQuantity(product.getStockQuantity() - dto.getQuantity());
                    productRepository.save(product);
                    ProductItem productItem = new ProductItem();
                    productItem.setQuantity(dto.getQuantity());
                    productItem.setPurchase(newPurchase);
                    productItem.setProduct(product);
                    productItem.setSubTotal(product.getPrice().multiply(new BigDecimal(dto.getQuantity())));
                    return productItem;
                }).collect(Collectors.toList());
    }

    private Payment extractPayment(Purchase purchase, RegisterPaymentDTO dto, BigDecimal totalPrice) {
        Payment payment = new Payment();
        payment.setPaymentMethod(dto.getPaymentMethod());
        payment.setPrice(totalPrice);
        payment.setPurchase(purchase);
        if (payment.getPaymentMethod() == PaymentMethod.CARTAO_CREDITO
                || payment.getPaymentMethod() == PaymentMethod.CARTAO_DEBITO) {
            Integer idCreditCard = dto.getIdCreditCard();
            CreditCard creditCard = creditCardRepository
                    .findById(idCreditCard)
                    .orElseThrow(() -> new NotFoundException("credit card"));
            payment.setCreditCard(creditCard);

            if (payment.getPaymentMethod() == PaymentMethod.CARTAO_CREDITO)
                payment.setInstallments(dto.getInstallments());
        }
        return payment;
    }

    private ResponsePurchaseDTO toDTO(Purchase purchase) {
        Integer idDescount = null;
        if (purchase.getDiscount() != null)
            idDescount = purchase.getDiscount().getId();
        return ResponsePurchaseDTO.builder()
                .id(purchase.getId())
                .idCustomer(purchase.getCustomer().getId())
                .productItems(toDTOProductItems(purchase.getProductItems()))
                .totalPrice(purchase.getTotalPrice())
                .date(purchase.getDate())
                .payment(toPaymentDTO(purchase.getPayment()))
                .idAddress(purchase.getAddress().getId())
                .shippingTax(purchase.getShippingTax())
                .idDiscount(idDescount)
                .build();
    }

    private List<ResponsePurchaseDTO> toDTOList(List<Purchase> purchases) {
        if (CollectionUtils.isEmpty(purchases)) {
            return Collections.emptyList();
        }
        return purchases.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private List<ProductItemDTO> toDTOProductItems(List<ProductItem> productItems) {
        if (CollectionUtils.isEmpty(productItems)) {
            return Collections.emptyList();
        }
        return productItems.stream().map(
                item -> ProductItemDTO
                        .builder()
                        .idProduct(item.getId())
                        .quantity(item.getQuantity())
                        .subTotal(item.getSubTotal())
                        .build())
                .collect(Collectors.toList());
    }

    private ResponsePaymentDTO toPaymentDTO(Payment payment) {
        ResponsePaymentDTO response = ResponsePaymentDTO
                .builder()
                .id(payment.getId())
                .paymentMethod(payment.getPaymentMethod())
                .price(payment.getPrice())
                .build();

        if (payment.getPaymentMethod() == PaymentMethod.CARTAO_CREDITO)
            response.setCreditCard(toCreditCardDTO(payment));

        if (payment.getPaymentMethod() == PaymentMethod.CARTAO_DEBITO)
            response.setDebitCard(toDebitCardDTO(payment));

        return response;
    }

    private ResponsePaymentCreditCardDTO toCreditCardDTO(Payment payment) {
        return ResponsePaymentCreditCardDTO
                .builder()
                .idCreditCard(payment.getCreditCard().getId())
                .installments(payment.getInstallments())
                .build();
    }

    private ResponsePaymentDebitCardDTO toDebitCardDTO(Payment payment) {
        return ResponsePaymentDebitCardDTO
                .builder()
                .idCreditCard(payment.getCreditCard().getId())
                .build();
    }

    private BigDecimal getTotalPrice(List<ProductItem> productItems) {
        BigDecimal totalValue = BigDecimal.ZERO;
        for (ProductItem productItem : productItems) {
            totalValue = totalValue
                    .add(productItem.getProduct().getPrice().multiply(new BigDecimal(productItem.getQuantity())));
        }
        return totalValue;
    }

    private BigDecimal calculateDescount(BigDecimal totalPrice, Discount discount) {
        BigDecimal descount = totalPrice.multiply(new BigDecimal(discount.getRate())).divide(BigDecimal.valueOf(100), 2,
                RoundingMode.HALF_UP);
        BigDecimal newTotalPrice = totalPrice.subtract(descount);
        return newTotalPrice;
    }

}
