package org.acme.services;
import java.util.ArrayList;
import java.util.List;
import org.acme.models.Customer;
import dtu.ws.fastmoney.User;

public class CustomerService {
private List<Customer> customers = new ArrayList<>();

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomer(User user, String accountId) {
        Customer newCustomer = new Customer();

        newCustomer.setFirstName(user.getFirstName());
        newCustomer.setLastName(user.getLastName());
        newCustomer.setCprNumber(user.getCprNumber());
        newCustomer.setBankAccount(accountId);
        customers.add(newCustomer);  // Adds the new payment to the list
    }
}