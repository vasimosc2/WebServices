package models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Payment {
    private int amount;
    private String customerId;
    private String merchantId;

    public Payment() {}

}