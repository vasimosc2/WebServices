/**
 * @primary-author Vasileios Moschou (s222566)
 *
 *
 */

package services;

import java.math.BigDecimal;

import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.BankServiceService;
import exceptions.PaymentException;
import infrastructure.repositories.Payments;
import infrastructure.repositories.interfaces.IPayments;
import models.*;
import services.interfaces.IPaymentService;


@jakarta.enterprise.context.ApplicationScoped
public class PaymentService implements IPaymentService {

    private final IPayments payments = Payments.getInstance();

    BankService bankService = new BankServiceService().getBankServicePort();

    public void clear() {
        payments.clear();
    }

    @Override
    public MoneyTransferredObject requestPayment(Customer customer, Merchant merchant, BigDecimal money, String tokenId) throws PaymentException {

        try {
            System.out.println(customer.getFirstName());
            System.out.println(
                "Before transaction customer has " + bankService.getAccount(customer.getBankAccount()).getBalance()
            );
            bankService.transferMoneyFromTo(customer.getBankAccount(), merchant.getBankAccount(), money, "Random Reason");
            System.out.println(
                "after transaction customer has " + bankService.getAccount(customer.getBankAccount()).getBalance()
            );
        } catch (BankServiceException_Exception e) {
            e.printStackTrace();
            throw new PaymentException("Payment failed because of: " + e.getMessage());
        }

        MoneyTransferredObject moneyTransfer = new MoneyTransferredObject(tokenId, customer.getId(), merchant.getId(), money);

        payments.addMoneyTransferredObject(moneyTransfer);

        return moneyTransfer;
    }


}









