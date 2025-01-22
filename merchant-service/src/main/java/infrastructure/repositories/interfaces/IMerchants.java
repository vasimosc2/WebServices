package infrastructure.repositories.interfaces;

import exceptions.account.AccountNotFoundException;
import models.Merchant;

public interface IMerchants extends IRepository<Merchant> {
    void clear();
    Merchant getById(String merchantId);
    Merchant getByCpr(String cpr);
    void remove(String merchantId) throws AccountNotFoundException;
}
