package infrastructure.repositories.interfaces;

import java.util.List;

import exceptions.TokenException;
import models.Token;

public interface ITokens{
    void clear();
    List<Token> get(String customerId);
    void update(String customerId, Token token);
    String getCustomerIdByTokenId(String tokenId) throws TokenException;

    boolean checkTokenIsValid(String tokenId) throws TokenException;

}
