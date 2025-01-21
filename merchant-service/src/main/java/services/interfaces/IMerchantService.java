package services.interfaces;
import models.MerchInt;
import models.Merchant;
import exceptions.account.AccountExistsException;
import exceptions.account.AccountNotFoundException;
import exceptions.account.BankAccountException;
import dtu.ws.fastmoney.BankServiceException_Exception;




public interface IMerchantService {
    void clear();
    String register(MerchInt merchInt) throws BankServiceException_Exception,AccountExistsException, BankAccountException ;
    Merchant get(String id) throws AccountNotFoundException;
    void retireAccountByCpr(String id) throws BankAccountException;

}
