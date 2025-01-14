package org.acme.services;
import java.util.ArrayList;
import java.util.List;

import org.acme.models.Customer;
import org.acme.models.Merchant;
import dtu.ws.fastmoney.User;
import org.apache.commons.lang3.RandomStringUtils;

public class MerchantService {
    private List<Merchant> merchants = new ArrayList<>();

    public List<Merchant> getMerchants() {
        return merchants;
    }

    public String setMerchant(Merchant merchant) {
        String merchantId;
        do {
            merchantId = "MERC-" + RandomStringUtils.randomNumeric(8);
        } while (isMerchantIdPresent(merchantId));

        merchant.setId(merchantId);
        merchants.add(merchant); // Add the merchant to the list

        return merchantId; // Return the unique merchantId
    }

    private boolean isMerchantIdPresent(String merchantId) {
        // Check if any merchant in the list has the same maerchantId
        for (Merchant m : merchants) {
            if (m.getId().equals(merchantId)) {
                return true;
            }
        }
        return false;
    }

}