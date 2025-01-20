package infrastructure.repositories;

import models.Customer;
import infrastructure.repositories.interfaces.ICustomers;

import java.util.ArrayList;
import java.util.List;

@jakarta.enterprise.context.ApplicationScoped
public class CustomersList implements ICustomers {

    private static CustomersList instance = null;

    private final List<Customer> Customers;

    private CustomersList() {
        Customers = new ArrayList<>();
    }

    public static CustomersList getInstance() {
        if (instance == null)
            instance = new CustomersList();
        return instance;
    }

    @Override
    public void clear() {
        Customers.clear();
    }

    @Override
    public void add(Customer Customer) {
        Customers.add(Customer);
    }



    @Override
    public Customer getByCpr(String cpr) {
        return Customers.stream()
                .filter(a -> a.getCprNumber().equals(cpr))
                .findAny()
                .orElse(null);
    }

    @Override
    public void remove(String cpr) {
        Customer Customer = getByCpr(cpr);
        if (Customer != null) {
            Customers.remove(Customer);
        }
    }

    @Override
    public List<Customer> getAll() {
        return Customers;
    }

}
