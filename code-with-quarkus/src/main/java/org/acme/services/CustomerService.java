package org.acme.services;

import java.util.ArrayList;
import java.util.List;

import org.acme.models.Customer;

public class CustomerService {
private List<Customer> customers = new ArrayList<>();
    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomer(Customer given_customer) {
        Customer newCustomer = new Customer();

        newCustomer.setId(given_customer.getId());
        newCustomer.setFirstName(given_customer.getFirstName());
        newCustomer.setLastName(given_customer.getLastName());
        newCustomer.setCprNumber(given_customer.getCprNumber());
        newCustomer.setBankAccount(given_customer.getBankAccount());
        customers.add(newCustomer);  // Adds the new payment to the list
    }
}


