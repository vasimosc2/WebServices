/**
 * @primary-author Vasileios Moschou (s222566)
 * @co-author Marcu Muro (s233662)
 *
 */
package services.interfaces;
import models.Customer;
import exceptions.account.AccountExistsException;
import exceptions.account.AccountNotFoundException;




public interface ICustomerService {
    void clear();
    String register(Customer customer) throws AccountExistsException;
    Customer getCustomerById(String customerId) throws AccountNotFoundException;
    String retireAccount(String customerId) throws AccountNotFoundException;

}
