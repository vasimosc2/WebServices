/**
 * @primary-author Vasileios Moschou (s222566)
 *
 *
 */
package infrastructure.repositories;
import infrastructure.repositories.interfaces.IPayments;
import models.MoneyTransferredObject;
import java.util.ArrayList;
import java.util.List;

@jakarta.enterprise.context.ApplicationScoped
public class Payments implements IPayments {

   private static Payments instance = null;

    private final List<MoneyTransferredObject> paymentStoreManager;

    // Private constructor for singleton pattern
    private Payments() {
        paymentStoreManager = new ArrayList<>();
    }

    // Public method to get the singleton instance
    public static Payments getInstance() {
        if (instance == null) {
            synchronized (Payments.class) {
                if (instance == null) {
                    instance = new Payments();
                }
            }
        }
        return instance;
    }


    @Override
    public void clear() {
        paymentStoreManager.clear();
    }


    @Override
    public void addMoneyTransferredObject(MoneyTransferredObject paymentManager) {
        paymentStoreManager.add(paymentManager);
    }


    @Override
    public List<MoneyTransferredObject> getAllPayments(){
        return paymentStoreManager;
    }



}