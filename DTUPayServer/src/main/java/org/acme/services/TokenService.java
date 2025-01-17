package org.acme.services;

import java.lang.foreign.Linker.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.acme.models.Token;
import org.acme.repositories.TokenRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class TokenService {
    
    @Inject
    TokenRepository tokenRepository;

    public List<Token> generateToken(String customerId, int count){

        if (count < 1 || count > 5) {
            throw new IllegalArgumentException("You can only request 1 to 5 tokens");
        }

        List<Token> unused = tokenRepository.findAllUnusedTokensByCustomerId(customerId);
        if (unused.size() > 6) {
            throw new IllegalArgumentException("You can only have a maximum of 6 tokens");
        }

        
        List<Token> tokens = new ArrayList<>();
        
        for(int i = 0; i < count; i++){
            String tokenId = UUID.randomUUID().toString();
            Token token = new Token(tokenId, customerId, false);
            tokenRepository.save(token);
            tokens.add(token);
        }
        return tokens;
    }

    public Token validateToken(String tokenId){
        Optional<Token> tokenOption = tokenRepository.findByTokenId(tokenId);
        if (tokenOption.isEmpty()){
            throw new IllegalArgumentException("Token is not empty");
        }

        Token token = tokenOption.get();
        if (token.isUsed()){
            throw new IllegalArgumentException("Token is already used");
        }
        return token;
    }

    public void markToken(String tokenId){
        Optional<Token> tokenOption = tokenRepository.findByTokenId(tokenId);
        if (tokenOption.isEmpty()){
            throw new IllegalArgumentException("Token is not empty");
        }
        
        Token token = tokenOption.get();
        token.setUsed(true);
        tokenRepository.update(token);
    }

}
