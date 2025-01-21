package infrastructure.repositories;


import models.Payment;
import models.Token;
import infrastructure.repositories.interfaces.IPayments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@jakarta.enterprise.context.ApplicationScoped
public class PaymentMap implements IPayments {

   private static PaymentMap instance = null;

    // Internal map to store tokens
    private final Map<String, Payment> paymentStore;

    // Private constructor for singleton pattern
    private PaymentMap() {
        paymentStore = new HashMap<>();
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
        paymentStore.clear();
    }


    @Override
    public  Payment get(String paymentId){
        return paymentStore.getOrDefault(paymentId, new Payment());
    }



    @Override
    public void add(Map<String, Payment> obj) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'add'");
    }


}