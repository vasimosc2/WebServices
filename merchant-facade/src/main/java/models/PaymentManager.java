package models;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PaymentManager {
    private BigDecimal amount;
    private String tokenId;
    private String customerId;
    private String merchantId;
}
