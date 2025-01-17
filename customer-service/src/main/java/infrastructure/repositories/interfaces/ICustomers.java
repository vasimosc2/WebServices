package infrastructure.repositories.interfaces;

import models.Customer;

public interface ICustomers extends IRepository<Customer> {
    void clear();
    Customer getById(String id);
    Customer getByCpr(String cpr);
    void remove(String id);
}
