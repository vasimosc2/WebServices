package services;

import dtu.ws.fastmoney.Account;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.BankServiceService;
import dtu.ws.fastmoney.User;
import exceptions.account.AccountExistsException;
import exceptions.account.AccountNotFoundException;
import exceptions.account.AccountRegistrationException;
import exceptions.account.BankAccountException;
import infrastructure.bank.*;
import infrastructure.repositories.CustomersList;
import infrastructure.repositories.interfaces.ICustomers;
import models.Customer;
import services.interfaces.ICustomerService;

import javax.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class CustomerService implements ICustomerService {

    private final BankService bankService = new BankServiceService().getBankServicePort();

    private final ICustomers repo = CustomersList.getInstance(); // Icustomer is the interface of the List of customers , which have the search methods and the remove
                                                                // At run time it will <turn> at  CustomerList and the getInstance initiallizes an empty list

    public void clear() {
        repo.clear();
    }

    @Override
    public String register(Customer customer, int money )
            throws AccountExistsException {

        if (isRegistered(customer)) {// This function is initialized later it just returns a bool of whether this user is registered
            throw new AccountExistsException("Account with cpr (" + creationRequest.getCprNumber() + ") already exists!");
        }


        String accountId = null;
        
        // Here we will try to fetch the account, if the account do exist the we must not register this Customer in the Bank

        Account potentialAccount = getBankAccountByCpr(customer.getCprNumber());
        if(potentialAccount == null){
            // If I get in here it means , that the account was not created and we must create it
            accountId =  registerBankAccount(customer,money);
            customer.setBankAccount(accountId);
            customer.setId(UUID.randomUUID().toString());
            repo.add(customer);
            return customer.getId();
        }

        else{
            //This means that the account exist
            customer.setBankAccount(potentialAccount.getId());
            customer.setId(UUID.randomUUID().toString());
            repo.add(customer);
            return customer.getId();
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

        try {
            return getUserAccountFromInfo(customer);
        } catch (BankAccountException e) {
            throw new AccountNotFoundException("Account with cpr (" + cpr + ") is not found!");
        }
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
        user.setCprNumber(userRegistrationDTO.getCprNumber());
        user.setFirstName(userRegistrationDTO.getFirstName());
        user.setLastName(userRegistrationDTO.getLastName());

        // try to create a bank account for the user
        String bankId;
        try {
            bankId = bankService.createAccountWithBalance(user, BigDecimal.valueOf(money));
        } catch (BankServiceException_Exception e) {
            throw new BankAccountException("Failed to create bank account" +
                    " for account with cpr (" + userRegistrationDTO.getCprNumber() + ")");
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
