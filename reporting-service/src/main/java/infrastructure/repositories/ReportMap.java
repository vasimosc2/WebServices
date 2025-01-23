package infrastructure.repositories;


import exceptions.TokenException;
import models.Payment;
import models.PaymentCustomer;
import models.PaymentMerchant;
import models.Token;
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
        tokenStore.clear();
    }


    @Override
    public  List<Token> get(String customerId){
        return tokenStore.getOrDefault(customerId, new ArrayList<>());
    }

    @Override
    public void update(String customerId, Token token) {
    // If the customerId exists, add the token to the list; otherwise, create a new list
    tokenStore.merge(customerId, new ArrayList<>(List.of(token)), (existingTokens, newTokens) -> {
        existingTokens.addAll(newTokens);
        return existingTokens;
    });
    }

    @Override
    public void invalidateToken(String tokenId) throws TokenException {

        for (Map.Entry<String, List<Token>> entry : tokenStore.entrySet()) {
            List<Token> tokens = entry.getValue();
            for (Token t : tokens) {
                if (t.getId().equals(tokenId)) {
                    System.out.println("Token " + tokenId + " has following usedValue: " + tokenStore.get(entry.getKey()));
                    t.setUsed(true);
                    System.out.println("Token " + tokenId + " marked as used USEDVALUE: " + tokenStore.get(entry.getKey()) + " for customer: " + entry.getKey());
                    return;
                }
            }
        }
        System.out.println("Token with ID: " + tokenId + " not found");
        throw new TokenException("Token with ID: " + tokenId + " not found");
    }

    @Override
    public String getCustomerIdByTokenId(String tokenId) throws TokenException {
        for (Map.Entry<String, List<Token>> entry : tokenStore.entrySet()) {
            List<Token> tokens = entry.getValue();
            for (Token t : tokens) {
                if (t.getId().equals(tokenId)) {
                    System.out.println("Token " + tokenId + " found for customer: " + entry.getKey());
                    return entry.getKey();
                }
            }
        }
        throw new TokenException("Token with ID: " + tokenId + " not found");
    }

    public void updateTokenInTokenStore(String tokenId) {

    }



    @Override
    public void add(Map<String, List<Token>> obj) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'add'");
    }

   

}