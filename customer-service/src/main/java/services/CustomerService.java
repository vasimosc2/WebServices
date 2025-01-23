package services;



import exceptions.account.AccountExistsException;
import exceptions.account.AccountNotFoundException;

import infrastructure.repositories.CustomersList;
import infrastructure.repositories.interfaces.ICustomers;
import models.Customer;
import services.interfaces.ICustomerService;
import java.util.UUID;

@jakarta.enterprise.context.ApplicationScoped
public class CustomerService implements ICustomerService {

    private final ICustomers repo = CustomersList.getInstance(); 

    public void clear() {
        repo.clear();
    }

    @Override
    public String register(Customer customer) throws AccountExistsException {

        if (isRegistered(customer)) {
            throw new AccountExistsException("Customer with cpr (" + customer.getCprNumber() + ") already exists!");
        }
        customer.setId("CUST-"+ UUID.randomUUID());
        repo.add(customer);
        return customer.getId();
        
    }

    @Override
    public Customer getAccount(String customerId) throws AccountNotFoundException {
        Customer customer = repo.getById(customerId);

        if (customer == null) {
            throw new AccountNotFoundException("Customer with customerID (" + customerId + ") is not found!");
        }

       return customer;
    }


    @Override
    public Customer getCustomerById(String customerId) throws AccountNotFoundException {
        Customer customer = repo.getById(customerId);

        if (customer == null) {
            throw new AccountNotFoundException("Customer with Cpr (" + customerId + ") is not found!");
        }

       return customer;
    }


    @Override
    public String retireAccount(String customerId) throws AccountNotFoundException {
        Customer customer = repo.getById(customerId);

        if (customer == null) {
            return null;
        }
        removeAccountFromRepo(customer);
        return customer.getId();
    }




    // HELPERS

    private boolean isRegistered(Customer customer) {
        Customer potentialCustomer = repo.getByCpr(customer.getCprNumber());
        return potentialCustomer != null;
    }




    private void removeAccountFromRepo(Customer customer) throws AccountNotFoundException {
        repo.remove(customer.getId());

    }
}
