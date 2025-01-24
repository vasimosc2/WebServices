/**
 * @primary-author Vasileios Moschou (s222566)
 *
 *
 */
package services.interfaces;
import models.Merchant;
import exceptions.account.AccountExistsException;
import exceptions.account.AccountNotFoundException;



public interface IMerchantService {
    void clear();
    String register(Merchant merchant) throws AccountExistsException;
    Merchant getMerchantById(String merchantId) throws AccountNotFoundException;
    void retireAccount(String merchantId) throws AccountNotFoundException;

}
