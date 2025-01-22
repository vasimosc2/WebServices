package services.interfaces;
import models.Customer;
import exceptions.account.AccountExistsException;
import exceptions.account.AccountNotFoundException;
import exceptions.account.BankAccountException;
import dtu.ws.fastmoney.BankServiceException_Exception;




public interface ICustomerService {
    void clear();
    String register(Customer customer) throws AccountExistsException;
    Customer getAccount(String customerId) throws AccountNotFoundException;
    String retireAccount(String customerId) throws AccountNotFoundException;

}
