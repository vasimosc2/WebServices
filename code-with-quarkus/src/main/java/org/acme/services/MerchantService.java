package org.acme.services;

import org.acme.models.Merchant;

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
