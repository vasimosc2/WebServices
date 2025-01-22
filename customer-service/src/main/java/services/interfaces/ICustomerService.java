package services.interfaces;
import models.CustInt;
import models.Customer;
import exceptions.account.AccountExistsException;
import exceptions.account.AccountNotFoundException;
import exceptions.account.BankAccountException;
import dtu.ws.fastmoney.BankServiceException_Exception;




public interface ICustomerService {
    void clear();
    String register(Customer customer) throws BankServiceException_Exception,AccountExistsException, BankAccountException ;
    Customer get(String cpr) throws AccountNotFoundException;
    Customer getCustomerById(String customerId) throws AccountNotFoundException;
    String retireAccountByCpr(String id) throws BankAccountException;

}
