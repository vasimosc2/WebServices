package infrastructure.repositories.interfaces;

import java.util.List;
import java.util.Map;

import exceptions.TokenException;
import models.Token;

public interface ITokens extends IRepository<Map<String, List<Token>>> {
    void clear();
    List<Token> get(String customerId);
    void update(String customerId, Token token);
    void invalidateToken(String tokenId) throws TokenException;

    String getCustomerIdByTokenId(String tokenId) throws TokenException;

}
