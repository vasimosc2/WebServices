package infrastructure.repositories;


import models.PaymentCustomer;
import infrastructure.repositories.interfaces.IPayments;
import models.PaymentManager;
import models.PaymentMerchant;
import models.Token;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@jakarta.enterprise.context.ApplicationScoped
public class PaymentMap implements IPayments {

   private static PaymentMap instance = null;

    // Internal map to store tokens
    private final Map<String, List<PaymentCustomer>> paymentStoreCustomer;
    private final Map<String, List<PaymentMerchant>> paymentStoreMerchant;
    private final List<PaymentManager> paymentStoreManager;

    // Private constructor for singleton pattern
    private PaymentMap() {
        paymentStoreCustomer = new HashMap<>();
        paymentStoreMerchant = new HashMap<>();
        paymentStoreManager = new ArrayList<>();
    }

    // Public method to get the singleton instance
    public static PaymentMap getInstance() {
        if (instance == null) {
            synchronized (PaymentMap.class) {
                if (instance == null) {
                    instance = new PaymentMap();
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
    public void addPaymentManager(PaymentManager paymentManager) {
        paymentStoreManager.add(paymentManager);
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
    public List<PaymentManager> getAllPayments(){
        return paymentStoreManager;
    }



    @Override
    public void add(Map<String, PaymentCustomer> obj) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'add'");
    }


}