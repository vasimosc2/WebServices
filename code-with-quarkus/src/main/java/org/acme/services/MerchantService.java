package org.acme.services;

import java.util.ArrayList;
import java.util.List;

import org.acme.models.Merchant;

public class MerchantService {
    private List<Merchant> merchants = new ArrayList<>();

    public List<Merchant> getMerchants() {
        return merchants;
    }

    public void setMerchant(Merchant given_merchant) {
        Merchant newMerchant = new Merchant(
                given_merchant.getName()
        );
        newMerchant.setId(given_merchant.getId());
        merchants.add(newMerchant);  // Adds the new merchant to the list
    }
}
