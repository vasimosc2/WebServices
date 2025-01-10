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
        Customer newCustomer = new Customer(
                given_customer.getName()
        );
        newCustomer.setId(given_customer.getId());
        customers.add(newCustomer);  // Adds the new payment to the list
    }
}


