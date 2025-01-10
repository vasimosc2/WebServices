package org.acme;

public class MerchantService {
    Merchant merchant = new Merchant("Vasilis");
    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant.setName(merchant.getName());
        this.merchant.setId(merchant.getId());
    }

}
