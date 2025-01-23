package dtu.example.models;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Payment {
    private String tokenId;
    private String customerId;
    private String merchantId;
    private BigDecimal amount;

    public Payment(String tokenId, String customerId, String merchantId, BigDecimal amount) {
        this.tokenId = tokenId;
        this.customerId = customerId;
        this.merchantId = merchantId;
        this.amount = amount;
    }

    public Payment() {}


}
