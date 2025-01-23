package models;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PaymentMerchant {
    private BigDecimal amount;
    private String tokenId;

    public PaymentMerchant() {}
}