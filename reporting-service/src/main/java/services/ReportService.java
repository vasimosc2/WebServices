/**
 * @primary-author Marco Muro (s233662)
 * @co-author Jan Ariel Ocampo s135214
 *
 */
package services;

import infrastructure.repositories.ReportMap;
import infrastructure.repositories.interfaces.IReports;

import models.*;
import services.interfaces.IReportService;

import java.util.List;

@jakarta.enterprise.context.ApplicationScoped
public class ReportService implements IReportService {

    private final IReports reportRepo = ReportMap.getInstance();

    public void clear() {
        reportRepo.clear();
    }

    @Override
    public void addMoneyTransferredToRepos(MoneyTransferredObject moneyTransferredObject) {
        PaymentCustomer paymentCustomer = new PaymentCustomer(moneyTransferredObject.getTokenId(), moneyTransferredObject.getMerchantId(), moneyTransferredObject.getAmount());
        PaymentMerchant paymentMerchant = new PaymentMerchant(moneyTransferredObject.getTokenId(), moneyTransferredObject.getAmount());
        Payment payment = new Payment(moneyTransferredObject.getTokenId(), moneyTransferredObject.getCustomerId(), moneyTransferredObject.getMerchantId(), moneyTransferredObject.getAmount());
        reportRepo.updateCustomerStore(moneyTransferredObject.getCustomerId(), paymentCustomer);
        reportRepo.updateMerchantStore(moneyTransferredObject.getMerchantId(), paymentMerchant);
        reportRepo.addPaymentManager(payment);
    }

    @Override
    public List<PaymentCustomer> getCustomerPaymentReport(String customerId) {
        return reportRepo.getPaymentsCustomer(customerId);
    }

    @Override
    public List<PaymentMerchant> getMerchantPaymentReport(String merchantId) {
        return reportRepo.getPaymentsMerchant(merchantId);
    }

    @Override
    public List<Payment> getManagerPaymentReport() {
        return reportRepo.getAllPayments();
    }


}









