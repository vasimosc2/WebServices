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
        Merchant newMerchant = new Merchant();

        newMerchant.setId(given_merchant.getId());
        newMerchant.setId(given_merchant.getId());
        newMerchant.setFirstName(given_merchant.getFirstName());
        newMerchant.setLastName(given_merchant.getLastName());
        newMerchant.setCprNumber(given_merchant.getCprNumber());
        newMerchant.setBankAccount(given_merchant.getBankAccount());

        merchants.add(newMerchant);  // Adds the new merchant to the list
    }
}
