package infrastructure.repositories;


import exceptions.TokenException;
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

    @Override
    public boolean checkTokenIsValid(String tokenId) throws TokenException {
        for (Map.Entry<String, List<Token>> entry : tokenStore.entrySet()) {
            List<Token> tokens = entry.getValue();
            for (Token t : tokens) {
                if (t.getId().equals(tokenId)) { // I found a token
                    if(t.getUsed() == false){
                        t.setUsed(true); // Here I invalidate the token
                        return true;
                    }
                    else{
                        return false;
                    }
                }
            }
        }
        throw new TokenException("Token with ID: " + tokenId + " not found");
    }

   

}