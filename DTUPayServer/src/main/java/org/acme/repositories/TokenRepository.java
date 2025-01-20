package org.acme.repositories;

import org.acme.exceptions.TokenException;
import org.acme.models.StakeholderId;
import org.acme.models.Token;

import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
@Startup // (optional) ensures it's created eagerly at startup rather than lazy
public class TokenRepository {
    private Map<String, List<Token>> tokensByCustomer = new HashMap<>();
    private Map<String, Token> tokensById = new HashMap<>();

    public List<Token> findAllTokens(){
        return new ArrayList<>(
                tokensById.values());
    }

    public void saveToken(String stakeholderId, Token token){
        String tokenId = token.getTokenId();

        tokensByCustomer.putIfAbsent(stakeholderId, new ArrayList<>());
        tokensByCustomer.get(stakeholderId).add(token);

        tokensById.put(tokenId, token);
    }

    public Optional<Token> findByTokenId(String tokenId){
        return Optional.ofNullable(tokensById.get(tokenId));
    }

    public String findCustomerIdByTokenId(String tokenId) throws TokenException {
        for (Map.Entry<String, List<Token>> entry : tokensByCustomer.entrySet()) {
            String customerId = entry.getKey();
            List<Token> tokens = entry.getValue();

            // Search for the tokenId in the customer's token list
            for (Token token : tokens) {
                if (token.getTokenId().equals(tokenId)) {
                    return customerId; // Return the customer ID if the tokenId is found
                }
            }
        }

        // If the tokenId is not found, return null or throw an exception
        throw new TokenException("Customer ID not found for token ID: " + tokenId);
    }

    public List<Token> findAllUnusedTokensByCustomerId(String customerId){
        return tokensByCustomer
            .getOrDefault(customerId, List.of())   // either the real list, or an empty list
            .stream()
            .filter(t -> !t.isUsed())
            .collect(Collectors.toList());            
        // return tokensByCustomer.getOrDefault(tokenId, List.<Token>of().stream().filter(t -> !t.isUsed()).collect(Collectors.toList()));
    }

    public void updateToken(String customerId, Token token){
        tokensById.put(token.getTokenId(), token);

        List<Token> list = tokensByCustomer.get(customerId);
        if(list != null){
            list.removeIf(t -> t.getTokenId().equals(token.getTokenId()));
            list.add(token);
        }
    }
}
