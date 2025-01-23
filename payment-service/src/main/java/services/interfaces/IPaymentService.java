package services.interfaces;
import exceptions.PaymentException;
import models.*;
import exceptions.TokenException;
import jakarta.ws.rs.core.Response;

import java.math.BigDecimal;


public interface IPaymentService {
    void clear();
    Response requestPayment(Customer customer, Merchant merchant, BigDecimal money, String tokenId) throws TokenException, PaymentException;

}
