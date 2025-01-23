package infrastructure.repositories;


import exceptions.account.AccountNotFoundException;
import models.Merchant;
import infrastructure.repositories.interfaces.IMerchants;

import java.util.ArrayList;
import java.util.List;

@jakarta.enterprise.context.ApplicationScoped
public class MerchantsList implements IMerchants {

    private static MerchantsList instance = null;

    private final List<Merchant> merchants;

    private MerchantsList() {
        merchants = new ArrayList<>();
    }

    public static MerchantsList getInstance() {
        if (instance == null)
            instance = new MerchantsList();
        return instance;
    }

    @Override
    public void clear() {
        merchants.clear();
    }

    @Override
    public void add(Merchant Customer) {
        merchants.add(Customer);
    }

    @Override
    public Merchant getById(String merchantId) {
        return merchants.stream()
                .filter(a -> a.getId().equals(merchantId))
                .findAny()
                .orElse(null);
    }

    @Override
    public Merchant getByCpr(String cpr) {
        return merchants.stream()
                .filter(a -> a.getCprNumber().equals(cpr))
                .findAny()
                .orElse(null);
    }

    @Override
    public void remove(String merchantId) throws AccountNotFoundException {
        Merchant merchant = getById(merchantId);
        if (merchant != null) {
            merchants.remove(merchant);
        }
        throw new AccountNotFoundException("Merchant with merchantID (" + merchantId + ") is not found!");
    }

}
