package services;

import dtu.ws.fastmoney.*;


import exceptions.account.AccountExistsException;
import exceptions.account.AccountNotFoundException;

import exceptions.account.BankAccountException;

import infrastructure.repositories.CustomersList;
import infrastructure.repositories.interfaces.ICustomers;
import models.CustInt;
import models.Customer;
import services.interfaces.ICustomerService;

import java.math.BigDecimal;
import java.util.UUID;

@jakarta.enterprise.context.ApplicationScoped
public class CustomerService implements ICustomerService {

    private final BankService bankService = new BankServiceService().getBankServicePort();

    private final ICustomers repo = CustomersList.getInstance(); 

    public void clear() {
        repo.clear();
    }

    @Override
    public String register(CustInt custInt) throws BankServiceException_Exception,AccountExistsException, BankAccountException {

        if (isRegistered(custInt.getCustomer())) {
            throw new AccountExistsException("Customer with cpr (" + custInt.getCustomer().getCprNumber() + ") already exists!");
        }


        String bankaccountId = null;

        System.out.println("I am here");
        Account potentialAccount = null;
        try{
            potentialAccount = bankService.getAccountByCprNumber(custInt.getCustomer().getCprNumber());
        } catch (BankServiceException_Exception e) {
                System.out.println("Customer not found, creating a new one.");
            }
        
        System.out.println(potentialAccount);
        
        if (potentialAccount == null) {
                bankaccountId = registerBankAccount(custInt.getCustomer(), custInt.getMoney());
                custInt.getCustomer().setBankAccount(bankaccountId);
                custInt.getCustomer().setId(UUID.randomUUID().toString());
                repo.add(custInt.getCustomer());
                return custInt.getCustomer().getId();
            } else {
                custInt.getCustomer().setBankAccount(potentialAccount.getId());
                custInt.getCustomer().setId(UUID.randomUUID().toString());
                repo.add(custInt.getCustomer());
                return custInt.getCustomer().getId();
            }
        
    }

    @Override
    public Customer get(String cpr) throws AccountNotFoundException {
        Customer customer = repo.getByCpr(cpr);

        if (customer == null) {
            throw new AccountNotFoundException("Customer with Cpr (" + cpr + ") is not found!");
        }

       return customer;
    }


    @Override
    public void retireAccountByCpr(String cpr) throws BankAccountException {
        Customer customer = repo.getByCpr(cpr);

        if (customer == null) {
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
