package infrastructure.repositories.interfaces;

import java.util.List;
import java.util.Map;

import models.PaymentCustomer;
import models.PaymentManager;
import models.PaymentMerchant;

public interface IPayments extends IRepository<Map<String, PaymentCustomer>> {
    void clear();

    void updateCustomerStore(String customerId, PaymentCustomer paymentCustomer);

    void updateMerchantStore(String merchantId, PaymentMerchant paymentMerchant);

    void addPaymentManager(PaymentManager paymentManager);

    List<PaymentCustomer> getPaymentsCustomer(String customerId);

    List<PaymentMerchant> getPaymentsMerchant(String merchantId);

    List<PaymentManager> getAllPayments();

}
