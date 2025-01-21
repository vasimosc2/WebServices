package services.interfaces;
import models.BankPay;
import models.TokenInt;
import models.Token;
import exceptions.TokenException;
import jakarta.ws.rs.core.Response;


public interface IPaymentService {
    void clear();
    Response requestPayment(BankPay bankPay) throws TokenException;

}
