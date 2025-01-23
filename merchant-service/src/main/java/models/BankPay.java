package models;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BankPay {
    private BigDecimal money;
    private String merchantId;
    private String tokenId;

    public BankPay(){}

    public BankPay(BigDecimal money, String tokenId, String merchantId) {
        this.money = money;
        this.tokenId = tokenId;
        this.merchantId = merchantId;
    };

}

