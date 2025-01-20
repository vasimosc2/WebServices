package infrastructure.repositories;


import models.Merchant;
import infrastructure.repositories.interfaces.IMerchants;

import java.util.ArrayList;
import java.util.List;

@jakarta.enterprise.context.ApplicationScoped
public class MerchantsList implements IMerchants {

    private static MerchantsList instance = null;

    private final List<Merchant> Merchants;

    private MerchantsList() {
        Merchants = new ArrayList<>();
    }

    public static MerchantsList getInstance() {
        if (instance == null)
            instance = new MerchantsList();
        return instance;
    }

    @Override
    public void clear() {
        Merchants.clear();
    }

    @Override
    public void add(Merchant Customer) {
        Merchants.add(Customer);
    }

    @Override
    public Merchant getByCpr(String cpr) {
        return Merchants.stream()
                .filter(a -> a.getCprNumber().equals(cpr))
                .findAny()
                .orElse(null);
    }

    @Override
    public void remove(String cpr) {
        Merchant Merchant = getByCpr(cpr);
        if (Merchant != null) {
            Merchants.remove(Merchant);
        }
    }

}
