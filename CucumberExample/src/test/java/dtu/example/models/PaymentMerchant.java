package dtu.example.models;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PaymentMerchant {
    private String tokenId;
    private BigDecimal amount;

    public PaymentMerchant(String tokenId, BigDecimal amount) {
        this.tokenId = tokenId;
        this.amount = amount;
    }

    public PaymentMerchant() {}
}