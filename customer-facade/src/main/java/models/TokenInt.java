/**
 * @primary-author Vasileios Moschou (s222566)
 * @co-author Angelos Michelis (s232488)
 *
 */
package models;

public class TokenInt {
    
    private String customerId;
    private int amount;

    public TokenInt() {
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
