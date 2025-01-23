package services.interfaces;
import models.TokenInt;
import models.Token;
import exceptions.TokenException;
import jakarta.ws.rs.core.Response;


public interface ITokenService {
    void clear();
    Response requestTokens(TokenInt tokenInt) throws TokenException;
    Token getFirstToken(String id) throws TokenException ;
    String getCustomerIdByTokenIdForPayment(String tokenId) throws TokenException;
    boolean isTokenValid(String tokenId) throws TokenException;
}
