package models;

import lombok.Getter;

@Getter
public class MoneyTransferredObject {
    private String customerId;
    private String merchantId;
    private String amount;
    private String tokenId;

    public MoneyTransferredObject(String customerId, String merchantId, String amount, String tokenId) {
        this.customerId = customerId;
        this.merchantId = merchantId;
        this.amount = amount;
        this.tokenId = tokenId;
    }

    public MoneyTransferredObject() {}
}
