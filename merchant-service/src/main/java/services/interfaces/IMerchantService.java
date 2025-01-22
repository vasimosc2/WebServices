package services.interfaces;
import models.MerchInt;
import models.Merchant;
import exceptions.account.AccountExistsException;
import exceptions.account.AccountNotFoundException;
import exceptions.account.BankAccountException;
import dtu.ws.fastmoney.BankServiceException_Exception;




public interface IMerchantService {
    void clear();
    String register(Merchant merchant) throws BankServiceException_Exception,AccountExistsException, BankAccountException ;
    Merchant get(String cpr) throws AccountNotFoundException;
    Merchant getMerchantById(String merchantId) throws AccountNotFoundException;
    void retireAccountByCpr(String id) throws BankAccountException;

}
