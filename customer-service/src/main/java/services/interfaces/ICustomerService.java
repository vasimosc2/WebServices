package services.interfaces;
import models.CustInt;
import models.Customer;
import exceptions.account.AccountExistsException;
import exceptions.account.AccountNotFoundException;
import exceptions.account.BankAccountException;
import dtu.ws.fastmoney.BankServiceException_Exception;




public interface ICustomerService {
    void clear();
    String register(CustInt custInt) throws BankServiceException_Exception,AccountExistsException, BankAccountException ;
    Customer get(String id) throws AccountNotFoundException;
    void retireAccountByCpr(String id) throws BankAccountException;

}
