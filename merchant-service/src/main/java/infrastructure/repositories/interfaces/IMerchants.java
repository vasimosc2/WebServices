package infrastructure.repositories.interfaces;

import models.Merchant;

public interface IMerchants extends IRepository<Merchant> {
    void clear();
    Merchant getByCpr(String cpr);
    void remove(String cpr);
}
