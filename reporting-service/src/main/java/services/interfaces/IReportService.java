package services.interfaces;
import models.*;
import exceptions.TokenException;

import java.util.List;


public interface IReportService {
    void clear();

    void addMoneyTransferredToRepos(MoneyTransferredObject moneyTransferredObject);
    List<PaymentCustomer> getCustomerPaymentReport(String customerId) throws TokenException;
    List<PaymentMerchant> getMerchantPaymentReport(String merchantId) throws TokenException;
    List<Payment> getManagerPaymentReport() throws TokenException;

}
