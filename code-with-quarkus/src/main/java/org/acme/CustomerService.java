package org.acme;

public class CustomerService {

   Customer customer = new Customer("Vasilis");
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer.setName(customer.getName());
        this.customer.setId(customer.getId());
    }

}
