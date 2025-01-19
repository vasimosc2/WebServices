package org.acme.services;
import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.models.Customer;
import org.acme.models.StakeholderId;
import org.apache.commons.lang3.RandomStringUtils;

@ApplicationScoped
public class CustomerService {

    private List<Customer> customers = new ArrayList<>();


    public List<Customer> getCustomers() {
        return customers;
    }

    public String registerCustomer(Customer customer) {
        String customerId;
        do {
            customerId = "CUST-" + RandomStringUtils.randomNumeric(8);
        } while (isCustomerIdPresent(customerId));

        customer.setId(new StakeholderId(customerId));
        customers.add(customer); // Add the customer to the list

        return customerId; // Return the unique customerId
    }

    private boolean isCustomerIdPresent(String customerId) {
        // Check if any customer in the list has the same customerId
        for (Customer c : customers) {
            if (c.getId().equals(customerId)) {
                return true;
            }
        }
        return false;
    }

    public Customer getCustomer(String customerId) {
        for (Customer c : customers) {
            if (c.getId().equals(customerId)) {
                return c;
            }
        }
        return null;
    }
}