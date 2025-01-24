/**
 * @primary-author Vasileios Moschou (s222566)
 *
 *
 */
package services;
import exceptions.account.AccountExistsException;
import exceptions.account.AccountNotFoundException;
import infrastructure.repositories.MerchantsList;
import infrastructure.repositories.interfaces.IMerchants;
import models.Merchant;
import services.interfaces.IMerchantService;
import java.util.UUID;

@jakarta.enterprise.context.ApplicationScoped
public class MerchantService implements IMerchantService {

  

    private final IMerchants repo = MerchantsList.getInstance(); 

    public void clear() {
        repo.clear();
    }

    @Override
    public String register(Merchant merchant) throws AccountExistsException {

        if (isRegistered(merchant)) {
            throw new AccountExistsException("Account with cpr (" + merchant.getCprNumber() + ") already exists!");
        }
        merchant.setId("MERC-"+ UUID.randomUUID());
        repo.add(merchant);
        return merchant.getId();

        
    }


    @Override
    public Merchant getMerchantById(String merchantId) throws AccountNotFoundException {
        Merchant merchant = repo.getById(merchantId);

        if (merchant == null) {
            throw new AccountNotFoundException("Account with Cpr (" + merchantId + ") is not found!");
        }

       return merchant;
    }


    @Override
    public void retireAccount(String merchantId) throws AccountNotFoundException {
        Merchant merchant = repo.getById(merchantId);

        if (merchant == null) {
            return;
        }
        removeAccountFromRepo(merchant);
    }




    // HELPERS

    private boolean isRegistered(Merchant merchant) {
        Merchant potentialMerchant = repo.getByCpr(merchant.getCprNumber());
        return potentialMerchant != null;
    }




    private void removeAccountFromRepo(Merchant merchant) throws AccountNotFoundException {
        repo.remove(merchant.getId());
    }
}
