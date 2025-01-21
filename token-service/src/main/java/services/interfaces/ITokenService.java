package services.interfaces;
import models.TokenInt;
import models.Token;
import exceptions.TokenException;
import exceptions.account.AccountExistsException;
import exceptions.account.AccountNotFoundException;
import exceptions.account.BankAccountException;
import jakarta.ws.rs.core.Response;
import dtu.ws.fastmoney.BankServiceException_Exception;




public interface ITokenService {
    void clear();
    Response requestTokens(TokenInt tokenInt) throws TokenException;
    Token getFirstToken(String id) throws TokenException ;

}
