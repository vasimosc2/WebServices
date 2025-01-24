/**
 * @primary-author Vasileios Moschou (s222566)
 *
 *
 */

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

        List<Token> unusedTokens = getUnusedTokens(tokenInt.getCustomerId());
        if (unusedTokens.size() > 1) {
            throw new TokenException(String.format("You already have %d tokens which exceed the maximum of 2, you can  generate more",unusedTokens.size()));
        }
        
        List<Token> generatedTokens = new ArrayList<>();

        for(int i = 0; i < tokenInt.getAmount(); i++){
            // Comment here you dont need to return the tokens, it is redundant 
            
            Token token = new Token(UUID.randomUUID().toString(), false);
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

        return tokenToReturn;
    }

    public String getCustomerIdByTokenIdForPayment(String tokenId) throws TokenException {
        String customerId = tokenmap.getCustomerIdByTokenId(tokenId);
        System.out.println("Token " + tokenId + " found for customer: " + customerId);
        return customerId;
    }

    @Override
    public boolean isTokenValid(String tokenId) throws TokenException {
        return tokenmap.checkTokenIsValid(tokenId);
    }
    @Override
    public void deleteToken(String customerId){
        tokenmap.deleteCustomerTokens(customerId);
    }




}









