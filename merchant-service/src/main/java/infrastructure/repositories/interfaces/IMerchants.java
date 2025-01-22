package infrastructure.repositories.interfaces;

import models.Merchant;

public interface IMerchants extends IRepository<Merchant> {
    void clear();
    Merchant getByCpr(String cpr);
    Merchant getById(String merchantId);
    void remove(String cpr);
}
