package services;

import dtu.ws.fastmoney.*;


import exceptions.account.AccountExistsException;
import exceptions.account.AccountNotFoundException;
import exceptions.account.AccountRegistrationException;
import exceptions.account.BankAccountException;

import infrastructure.repositories.CustomersList;
import infrastructure.repositories.interfaces.ICustomers;
import models.CustInt;
import models.Customer;
import services.interfaces.ICustomerService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@jakarta.enterprise.context.ApplicationScoped
public class CustomerService implements ICustomerService {

    private final BankService bankService = new BankServiceService().getBankServicePort();

    private final ICustomers repo = CustomersList.getInstance(); // Icustomer is the interface of the List of customers , which have the search methods and the remove
                                                                // At run time it will <turn> at  CustomerList and the getInstance initiallizes an empty list

    public void clear() {
        repo.clear();
    }

    @Override
    public String register(CustInt custInt) throws BankServiceException_Exception,AccountExistsException, BankAccountException {

        if (isRegistered(custInt.getCustomer())) {// This function is initialized later it just returns a bool of whether this user is registered
            throw new AccountExistsException("Account with cpr (" + custInt.getCustomer().getCprNumber() + ") already exists!");
        }


        String accountId = null;
        
        // Here we will try to fetch the account, if the account do exist the we must not register this Customer in the Bank
        
        Account potentialAccount = bankService.getAccountByCprNumber(custInt.getCustomer().getCprNumber());
            
            if (potentialAccount == null) {
                // If I get in here it means that the account was not created and we must create it
                accountId = registerBankAccount(custInt.getCustomer(), custInt.getMoney());
                custInt.getCustomer().setBankAccount(accountId);
                custInt.getCustomer().setId(UUID.randomUUID().toString());
                repo.add(custInt.getCustomer());
                return custInt.getCustomer().getId();
            } else {
                // This means the account exists
                custInt.getCustomer().setBankAccount(potentialAccount.getId());
                custInt.getCustomer().setId(UUID.randomUUID().toString());
                repo.add(custInt.getCustomer());
                return custInt.getCustomer().getId();
            }
        
    }

    @Override
    public Customer get(String id) throws AccountNotFoundException {
       Customer customer = repo.getById(id);

        if (customer == null) {
            throw new AccountNotFoundException("Account with id (" + id + ") is not found!");
        }

       return customer;
    }

    @Override
    public Customer getByCpr(String cpr) throws AccountNotFoundException {
        Customer customer = repo.getByCpr(cpr);

        if (customer == null) {
            throw new AccountNotFoundException("Account with cpr (" + cpr + ") is not found!");
        }

       return customer;
    }

    @Override
    public List<Customer> getAll() throws BankAccountException {
        return repo.getAll();// This is intefaced on IRepository and implemented on CustomerList
    }

    @Override
    public void retireAccountByCpr(String cpr) throws BankAccountException {
        Customer customer = repo.getByCpr(cpr);

        if (customer == null) { // user doesn't exist
            return;
        }

        retireAccountFromInfo(customer); // FIX this function
    }

    @Override
    public void retireAccount(String id) throws BankAccountException {
        Customer customer = repo.getById(id);

        if (customer == null) { // user doesn't exist
            return;
        }

        retireAccountFromInfo(customer);
    }

























    // HELPERS

    private boolean isRegistered(Customer customer) {
        Customer potentialCustomer = repo.getByCpr(customer.getCprNumber());
        return potentialCustomer != null;
    }

    private String registerBankAccount(Customer customer, int money)
            throws BankAccountException {

        User user = new User();
        user.setCprNumber(customer.getCprNumber());
        user.setFirstName(customer.getFirstName());
        user.setLastName(customer.getLastName());

        // try to create a bank account for the user
        String bankId;
        try {
            bankId = bankService.createAccountWithBalance(user, BigDecimal.valueOf(money));
        } catch (BankServiceException_Exception e) {
            throw new BankAccountException("Failed to create bank account" +
                    " for account with cpr (" + customer.getCprNumber() + ")");
        }

        return bankId;
    }



    private Account getBankAccountByCpr(String cpr) throws BankAccountException {
        try {
            return bankService.getAccountByCprNumber(cpr);
        } catch (BankServiceException_Exception e) {
            throw new BankAccountException(e.getMessage());
        }
    }

    private Account getBankAccount(String bankId) throws BankAccountException {
        try {
            return bankService.getAccount(bankId);
        } catch (BankServiceException_Exception e) {
            throw new BankAccountException(e.getMessage());
        }
    }

    private void retireAccountFromInfo(Customer customer)
            throws BankAccountException {
        try {
            bankService.retireAccount(customer.getBankAccount());
            repo.remove(customer.getId());
        } catch (BankServiceException_Exception e) {
            throw new BankAccountException(e.getMessage());
        }
    }
}
