package services;

import java.math.BigDecimal;

import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.BankServiceService;
import exceptions.PaymentException;
import infrastructure.repositories.PaymentMap;
import infrastructure.repositories.interfaces.IPayments;
import jakarta.ws.rs.core.Response;
import models.*;
import services.interfaces.IPaymentService;


@jakarta.enterprise.context.ApplicationScoped
public class PaymentService implements IPaymentService {

    private final IPayments paymentmap = PaymentMap.getInstance();

    BankService bankService = new BankServiceService().getBankServicePort();

    public void clear() {
        paymentmap.clear();
    }

    @Override
    public Response requestPayment(Customer customer, Merchant merchant, BigDecimal money, String tokenId) throws PaymentException {

        try {
            bankService.transferMoneyFromTo(customer.getBankAccount(), merchant.getBankAccount(), money, "Random Reason");
        } catch (BankServiceException_Exception e) {
            e.printStackTrace();
            throw new PaymentException("Payment failed because of: " + e.getMessage());
        }

        PaymentCustomer paymentCustomer = new PaymentCustomer();
        paymentCustomer.setAmount(money);
        paymentCustomer.setTokenId(tokenId);
        paymentCustomer.setMerchantId(merchant.getId());

        paymentmap.updateCustomerStore(customer.getId(), paymentCustomer);

        PaymentMerchant paymentMerchant = new PaymentMerchant();
        paymentMerchant.setAmount(money);
        paymentMerchant.setTokenId(tokenId);

        paymentmap.updateMerchantStore(merchant.getId(), paymentMerchant);

        PaymentManager paymentManager = new PaymentManager();
        paymentManager.setAmount(money);
        paymentManager.setTokenId(tokenId);
        paymentManager.setCustomerId(customer.getId());
        paymentManager.setMerchantId(merchant.getId());

        paymentmap.addPaymentManager(paymentManager);

        return Response.status(Response.Status.OK).build();
    }


}









