package models;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class MoneyTransferredObject {
    private String tokenId;
    private String customerId;
    private String merchantId;
    private BigDecimal amount;

    public MoneyTransferredObject(String tokenId, String customerId, String merchantId, BigDecimal amount) {
        this.tokenId = tokenId;
        this.customerId = customerId;
        this.merchantId = merchantId;
        this.amount = amount;
    }

    public MoneyTransferredObject() {}
}
