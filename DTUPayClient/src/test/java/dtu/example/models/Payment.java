package dtu.example.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Payment {
    private int amount;
    private String customerId;
    private String merchantId;
    
    public Payment() {}

    public Payment(int amount, String customerId, String merchantId) {
        this.amount = amount;
        this.customerId = customerId;
        this.merchantId = merchantId;
    }
}
