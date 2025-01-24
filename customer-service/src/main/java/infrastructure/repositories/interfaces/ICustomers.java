/**
 * @primary-author Vasileios Moschou (s222566)
 * @co-author Marcu Muro (s233662)
 *
 */
package infrastructure.repositories.interfaces;
import exceptions.account.AccountNotFoundException;
import models.Customer;

public interface ICustomers extends IRepository<Customer> {
    void clear();
    Customer getById(String customerId);
    Customer getByCpr(String cpr);
    void remove(String customerId) throws AccountNotFoundException;
}
