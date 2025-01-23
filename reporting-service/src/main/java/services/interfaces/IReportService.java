package services.interfaces;
import models.Payment;
import models.PaymentCustomer;
import models.PaymentMerchant;
import models.Token;
import exceptions.TokenException;

import java.util.List;


public interface IReportService {
    void clear();
    List<PaymentCustomer> getCustomerPaymentReport(String customerId) throws TokenException;
    List<PaymentMerchant> getMerchantPaymentReport(String merchantId) throws TokenException;
    List<Payment> getManagerPaymentReport() throws TokenException;

}
