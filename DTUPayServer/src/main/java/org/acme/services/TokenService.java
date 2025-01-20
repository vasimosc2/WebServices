package org.acme.services;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.acme.exceptions.TokenException;
import org.acme.models.StakeholderId;
import org.acme.models.Token;
import org.acme.repositories.TokenRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class TokenService {
    
    @Inject
    TokenRepository tokenRepository;

    public List<Token> getAllTokens(){
        return tokenRepository.findAllTokens();
    }

    public List<Token> generateToken(StakeholderId customerId, int count) throws TokenException {

        if (count < 1 || count > 5) {
            throw new TokenException("You can only request 1 to 5 tokens");
        }

        List<Token> unusedTokens = tokenRepository.findAllUnusedTokensByCustomerId(customerId);
        if (unusedTokens.size() > 6) {
            throw new TokenException("You can only have a maximum of 6 tokens");
        }

        
        List<Token> tokens = new ArrayList<>();
        
        for(int i = 0; i < count; i++){
            String tokenId = UUID.randomUUID().toString();
            Token token = new Token(tokenId, false);
            tokenRepository.saveToken(customerId, token);
            tokens.add(token);
        }
        return tokens;
    }

    public Token validateToken(String tokenId) throws TokenException {
        Optional<Token> tokenOption = tokenRepository.findByTokenId(tokenId);
        if (tokenOption.isEmpty()){
            throw new TokenException("TokenID is not found");
        }

        Token token = tokenOption.get();
        if (token.isUsed()){
            throw new TokenException("Token is already used");
        }
        return token;
    }

    public void markTokenAsUsed(StakeholderId customerId, String tokenId) throws TokenException {
        Optional<Token> tokenOption = tokenRepository.findByTokenId(tokenId);
        if (tokenOption.isEmpty()){
            throw new TokenException("TokenID is not found");
        }
        
        Token token = tokenOption.get();
        token.setUsed(true);
        tokenRepository.updateToken(customerId, token);
    }

}
