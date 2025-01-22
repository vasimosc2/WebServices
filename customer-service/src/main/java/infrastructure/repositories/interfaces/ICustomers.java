package infrastructure.repositories.interfaces;

import exceptions.account.AccountNotFoundException;
import models.Customer;

public interface ICustomers extends IRepository<Customer> {
    void clear();
    Customer getById(String customerId);

    Customer getByCpr(String cpr);
    Customer getByCustomerId(String customerId);
    void remove(String customerId) throws AccountNotFoundException;
}
