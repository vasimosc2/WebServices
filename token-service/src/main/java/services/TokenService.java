package services;

import infrastructure.repositories.TokenMap;
import infrastructure.repositories.interfaces.ITokens;
import jakarta.ws.rs.core.Response;
import models.Token;
import models.TokenInt;
import services.interfaces.ITokenService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import exceptions.TokenException;

@jakarta.enterprise.context.ApplicationScoped
public class TokenService implements ITokenService {

    private final ITokens tokenmap = TokenMap.getInstance(); 

    public void clear() {
        tokenmap.clear();
    }

    @Override
    public Response requestTokens(TokenInt tokenInt) throws TokenException {
        if (tokenInt.getAmount() < 1 || tokenInt.getAmount() > 5) {
            throw new TokenException("You can only request 1 to 5 tokens");
        }

        List<Token> unusedTokens = getUnusedTokens(tokenInt.getCustomerId()); // It does not matter
        if (unusedTokens.size() > 6) {
            throw new TokenException("You can only have a maximum of 6 tokens");
        }

        List<Token> generatedTokens = new ArrayList<>();

        for(int i = 0; i < tokenInt.getAmount(); i++){
            
            Token token = new Token(UUID.randomUUID().toString(), false);
            System.out.println(tokenInt.getCustomerId());
            tokenmap.update(tokenInt.getCustomerId(),token);
            generatedTokens.add(token);
        }

        System.out.println(tokenmap.get(tokenInt.getCustomerId()));
        return Response.ok(generatedTokens).build();
    }





    private List<Token> getUnusedTokens(String customerId){
         List<Token> tokens = tokenmap.get(customerId);
    
        if (tokens == null || tokens.isEmpty()) {
            return new ArrayList<>();
        }

        return tokens.stream()
                    .filter(token -> !token.getUsed())
                    .toList();
    }

    @Override
    public Token getFirstToken(String customerId) throws TokenException {
        List<Token> tokens = tokenmap.get(customerId);

        System.out.println("I reached tokenService");
        if (tokens == null || tokens.isEmpty()) {
            throw new TokenException("No tokens found for customer with ID: " + customerId);
        }

        Token tokenToReturn = tokens.stream()
                                    .filter(token -> !token.getUsed())
                                    .findFirst()
                                    .orElseThrow(() -> new TokenException("No unused tokens found for customer with ID: " + customerId));

        // token should be invalidated only after it has been used in a successfull payment
        // tokenmap.invalidateToken(customerId,tokenToReturn);

        return tokenToReturn;
    }



}









