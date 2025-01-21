package infrastructure.repositories;


import models.Token;
import infrastructure.repositories.interfaces.ITokens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@jakarta.enterprise.context.ApplicationScoped
public class TokenMap implements ITokens {

   private static TokenMap instance = null;

    // Internal map to store tokens
    private final Map<String, List<Token>> tokenStore;

    // Private constructor for singleton pattern
    private TokenMap() {
        tokenStore = new HashMap<>();
    }

    // Public method to get the singleton instance
    public static TokenMap getInstance() {
        if (instance == null) {
            synchronized (TokenMap.class) {
                if (instance == null) {
                    instance = new TokenMap();
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
    public void invalidateToken(String customerId, Token token){
        List<Token> tokens = tokenStore.get(customerId);

        if (tokens == null || tokens.isEmpty()) {
            System.out.println("No tokens found for customer with ID: " + customerId);
            return;
        }

        for (Token t : tokens) {
            if (t.getId().equals(token.getId())) {
                t.setUsed(true);
                System.out.println("Token " + token.getId() + " marked as used for customer: " + customerId);
                return;
            }
    }

    // If no matching token was found, print a message (or handle accordingly)
    System.out.println("Token with ID " + token.getId() + " not found for customer: " + customerId);
    }


    @Override
    public void add(Map<String, List<Token>> obj) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'add'");
    }

   

}