package services.interfaces;
import exceptions.PaymentException;
import models.*;
import exceptions.TokenException;

import java.math.BigDecimal;


public interface IPaymentService {
    void clear();
    MoneyTransferredObject requestPayment(Customer customer, Merchant merchant, BigDecimal money, String tokenId) throws TokenException, PaymentException;
}
