package org.acme.services;
import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.models.Merchant;
import org.acme.models.StakeholderId;
import org.apache.commons.lang3.RandomStringUtils;

@ApplicationScoped
public class MerchantService {

    private List<Merchant> merchants = new ArrayList<>();
    public List<Merchant> getMerchants() {
        return merchants;
    }

    public String registerMerchant(Merchant merchant) {
        String merchantId;
        do {
            merchantId = "MERC-" + RandomStringUtils.randomNumeric(8);
        } while (isMerchantIdPresent(merchantId));

        merchant.setStakeholderId(new StakeholderId(merchantId));
        merchants.add(merchant); // Add the merchant to the list

        return merchantId; // Return the unique merchantId
    }

    private boolean isMerchantIdPresent(String merchantId) {
        // Check if any merchant in the list has the same maerchantId
        for (Merchant m : merchants) {
            if (m.getStakeholderId().equals(merchantId)) {
                return true;
            }
        }
        return false;
    }

    public Merchant getMerchant(String merchantId) {
        for (Merchant m : merchants) {
            if (m.getStakeholderId().equals(merchantId)) {
                return m;
            }
        }
        return null;
    }

}