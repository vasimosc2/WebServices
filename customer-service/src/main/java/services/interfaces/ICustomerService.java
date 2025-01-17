package services.interfaces;
import models.Customer;
import exceptions.account.AccountExistsException;
import exceptions.account.AccountNotFoundException;
import exceptions.account.AccountRegistrationException;
import exceptions.account.BankAccountException;

import java.util.List;

public interface ICustomerService {
    void clear();
    String register(Customer customer, int money) throws AccountExistsException, AccountRegistrationException;
    Customer get(String id) throws AccountNotFoundException;
    Customer getByCpr(String cpr) throws AccountNotFoundException;
    List<Customer> getAll() throws BankAccountException;
    void retireAccount(String id) throws BankAccountException;
    void retireAccountByCpr(String cpr) throws BankAccountException;
}
