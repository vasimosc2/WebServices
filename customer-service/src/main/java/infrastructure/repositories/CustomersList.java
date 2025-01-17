package infrastructure.repositories;
// TODO add a remove for name and last name
import models.Customer;
import infrastructure.repositories.interfaces.ICustomers;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
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
    public Customer getById(String id) {
        return Customers.stream()
                .filter(a -> a.getId().equals(id))
                .findAny()
                .orElse(null);
    }

    @Override
    public Customer getByCpr(String cpr) {
        return Customers.stream()
                .filter(a -> a.getCprNumber().equals(cpr))
                .findAny()
                .orElse(null);
    }

    @Override
    public void remove(String id) {
        Customer Customer = getById(id);
        if (Customer != null) {
            Customers.remove(Customer);
        }
    }

    @Override
    public List<Customer> getAll() {
        return Customers;
    }

}
