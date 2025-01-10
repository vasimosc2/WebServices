package dtu.example.models;

public class Payment {
    private int amount;
    private String customerId;
    private String merchantId;
    
    public Payment() {}

    // Getter for amount
    public int getAmount() {
        return amount;
    }

    // Setter for amount
    public void setAmount(int amount) {
        this.amount = amount;
    }

    // Getter for customerId
    public String getCustomerId() {
        return customerId;
    }

    // Setter for customerId
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    // Getter for merchantId
    public String getMerchantId() {
        return merchantId;
    }

    // Setter for merchantId
    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    // Constructor with parameters
    public Payment(int amount, String customerId, String merchantId) {
        this.amount = amount;
        this.customerId = customerId;
        this.merchantId = merchantId;
    }
}
