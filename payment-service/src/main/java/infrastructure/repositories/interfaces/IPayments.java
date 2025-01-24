/**
 * @primary-author Vasileios Moschou (s222566)
 *  @co-author Angelos Michelis (s232488)
 *
 */
package infrastructure.repositories.interfaces;
import java.util.List;
import models.MoneyTransferredObject;


public interface IPayments {
    void clear();
    void addMoneyTransferredObject(MoneyTransferredObject paymentManager);
    List<MoneyTransferredObject> getAllPayments();

}
