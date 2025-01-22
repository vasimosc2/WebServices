package services.interfaces;
import models.Merchant;
import exceptions.account.AccountExistsException;
import exceptions.account.AccountNotFoundException;
import exceptions.account.BankAccountException;
import dtu.ws.fastmoney.BankServiceException_Exception;




public interface IMerchantService {
    void clear();
    String register(Merchant merchant) throws AccountExistsException;
    Merchant getAccount(String merchantId) throws AccountNotFoundException;
    void retireAccount(String merchantId) throws AccountNotFoundException;

}
