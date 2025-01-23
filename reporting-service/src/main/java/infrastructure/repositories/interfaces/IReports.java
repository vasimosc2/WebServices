package infrastructure.repositories.interfaces;

import java.util.List;
import java.util.Map;

import exceptions.TokenException;
import models.*;

public interface IReports extends IRepository<Map<String, List<MoneyTransferredObject>>> {
    void clear();
    void updateCustomerStore(String customerId, PaymentCustomer paymentCustomer);
    void updateMerchantStore(String merchantId, PaymentMerchant paymentMerchant);
    void addPaymentManager(Payment payment);
    List<PaymentCustomer> getPaymentsCustomer(String customerId);

    List<PaymentMerchant> getPaymentsMerchant(String merchantId);

    List<Payment> getAllPayments();

}
