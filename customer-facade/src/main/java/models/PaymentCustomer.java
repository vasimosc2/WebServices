/**
 * @primary-author Marcu Muro (s233662)
 * @co-author Kaizhi Fan (s240047)
 *
 *
 */
package models;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class PaymentCustomer {
    private String tokenId;
    private String merchantId;
    private BigDecimal amount;

    public PaymentCustomer(String tokenId, String merchantId, BigDecimal amount) {
        this.tokenId = tokenId;
        this.merchantId = merchantId;
        this.amount = amount;
    }

    public PaymentCustomer() {}

}