package org.acme.services;
import java.util.ArrayList;
import java.util.List;
import org.acme.models.Merchant;
import dtu.ws.fastmoney.User;

public class MerchantService {
    private List<Merchant> merchants = new ArrayList<>();

    public List<Merchant> getMerchants() {
        return merchants;
    }

    public void setMerchant(User user, String accountId) {
        Merchant newMerchant = new Merchant();

        newMerchant.setFirstName(user.getFirstName());
        newMerchant.setLastName(user.getLastName());
        newMerchant.setCprNumber(user.getCprNumber());
        newMerchant.setBankAccount(accountId);
        merchants.add(newMerchant);  // Adds the new merchant to the list
    }
}