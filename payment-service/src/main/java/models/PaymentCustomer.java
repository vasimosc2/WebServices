package models;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PaymentCustomer {
    private BigDecimal amount;
    private String tokenId;
    private String merchantId;

    public PaymentCustomer() {}

}