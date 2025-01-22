package infrastructure.repositories;

import exceptions.account.AccountNotFoundException;
import models.Customer;
import infrastructure.repositories.interfaces.ICustomers;

import java.util.ArrayList;
import java.util.List;

@jakarta.enterprise.context.ApplicationScoped
public class CustomersList implements ICustomers {

    private static CustomersList instance = null;

    private final List<Customer> customers;

    private CustomersList() {
        customers = new ArrayList<>();
    }

    public static CustomersList getInstance() {
        if (instance == null)
            instance = new CustomersList();
        return instance;
    }

    @Override
    public void clear() {
        customers.clear();
    }

    @Override
    public void add(Customer Customer) {
        customers.add(Customer);
    }



    @Override
    public Customer getById(String customerId) {
        return customers.stream()
                .filter(a -> a.getId().equals(customerId))
                .findAny()
                .orElse(null);
    }

    @Override
    public Customer getByCpr(String cpr) {
        return customers.stream()
                .filter(a -> a.getCprNumber().equals(cpr))
                .findAny()
                .orElse(null);
    }

    @Override
    public Customer getByCustomerId(String customerId) {
        return customers.stream()
                .filter(a -> a.getId().equals(customerId))
                .findAny()
                .orElse(null);
    }

    @Override
    public void remove(String customerId) throws AccountNotFoundException {
        Customer customer = getById(customerId);
        if (customer != null) {
            customers.remove(customer);
        }
        throw new AccountNotFoundException("Customer with customerID (" + customerId + ") is not found!");
    }

    @Override
    public List<Customer> getAll() {
        return customers;
    }

}
