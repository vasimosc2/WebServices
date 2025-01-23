package infrastructure.repositories;


import models.MoneyTransferredObject;
import models.Payment;
import models.PaymentCustomer;
import models.PaymentMerchant;
import infrastructure.repositories.interfaces.IReports;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@jakarta.enterprise.context.ApplicationScoped
public class ReportMap implements IReports {

   private static ReportMap instance = null;

    // Internal map to store payments
    private final Map<String, List<PaymentCustomer>> paymentStoreCustomer;
    private final Map<String, List<PaymentMerchant>> paymentStoreMerchant;
    private final List<Payment> paymentStoreManager;

    // Private constructor for singleton pattern
    private ReportMap() {
        paymentStoreCustomer = new HashMap<>();
        paymentStoreMerchant = new HashMap<>();
        paymentStoreManager = new ArrayList<>();
    }

    // Public method to get the singleton instance
    public static ReportMap getInstance() {
        if (instance == null) {
            synchronized (ReportMap.class) {
                if (instance == null) {
                    instance = new ReportMap();
                }
            }
        }
        return instance;
    }


    @Override
    public void clear() {
        paymentStoreCustomer.clear();
        paymentStoreMerchant.clear();
        paymentStoreManager.clear();
    }

    @Override
    public void updateCustomerStore(String customerId, PaymentCustomer paymentCustomer) {
        // If the customerId exists, add the token to the list; otherwise, create a new list
        paymentStoreCustomer.merge(customerId, new ArrayList<>(List.of(paymentCustomer)), (existingPayments, newPayments) -> {
            existingPayments.addAll(newPayments);
            return existingPayments;
        });
    }

    @Override
    public void updateMerchantStore(String merchantId, PaymentMerchant paymentMerchant) {
        // If the merchantId exists, add the token to the list; otherwise, create a new list
        paymentStoreMerchant.merge(merchantId, new ArrayList<>(List.of(paymentMerchant)), (existingPayments, newPayments) -> {
            existingPayments.addAll(newPayments);
            return existingPayments;
        });
    }

    @Override
    public void addPaymentManager(Payment payment) {
        paymentStoreManager.add(payment);
    }

    @Override
    public List<PaymentCustomer> getPaymentsCustomer(String customerId){
        return paymentStoreCustomer.getOrDefault(customerId, new ArrayList<PaymentCustomer>());
    }

    @Override
    public List<PaymentMerchant> getPaymentsMerchant(String customerId){
        return paymentStoreMerchant.getOrDefault(customerId, new ArrayList<PaymentMerchant>());
    }

    @Override
    public List<Payment> getAllPayments(){
        return paymentStoreManager;
    }


    @Override
    public void add(Map<String, List<MoneyTransferredObject>> obj) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'add'");

    }
}