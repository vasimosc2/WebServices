package org.acme.repositories;

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
    private Map<StakeholderId, List<Token>> tokensByCustomer = new HashMap<>();
    private Map<String, Token> tokensById = new HashMap<>();

    public List<Token> findAllTokens(){
        return new ArrayList<>(
                tokensById.values());
    }

    public void saveToken(StakeholderId stakeholderId, Token token){
        String tokenId = token.getTokenId();

        tokensByCustomer.putIfAbsent(stakeholderId, new ArrayList<>());
        tokensByCustomer.get(stakeholderId).add(token);

        tokensById.put(tokenId, token);
    }

    public Optional<Token> findByTokenId(String tokenId){
        return Optional.ofNullable(tokensById.get(tokenId));
    }

    public List<Token> findAllUnusedTokensByCustomerId(StakeholderId customerId){
        return tokensByCustomer
            .getOrDefault(customerId, List.of())   // either the real list, or an empty list
            .stream()
            .filter(t -> !t.isUsed())
            .collect(Collectors.toList());            
        // return tokensByCustomer.getOrDefault(customerId, List.<Token>of().stream().filter(t -> !t.isUsed()).collect(Collectors.toList()));
    }

    public void updateToken(StakeholderId customerId, Token token){
        tokensById.put(token.getTokenId(), token);

        List<Token> list = tokensByCustomer.get(customerId);
        if(list != null){
            list.removeIf(t -> t.getTokenId().equals(token.getTokenId()));
            list.add(token);
        }
    }
}
