package org.acme.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.BankServiceService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.exceptions.PaymentException;
import org.acme.exceptions.StakeholderException;
import org.acme.models.*;

@ApplicationScoped
public class PaymentService {

    @Inject
    CustomerService customerService;

    @Inject
    MerchantService merchantService;


    private BankService bankService = new BankServiceService().getBankServicePort();

    // List to store all payment objects
    private List<Payment> payments = new ArrayList<>();

    // Returns the list of all payments
    public List<Payment> getPayments() {
        return payments;
    }

    // Adds a new payment to the list
    public void setPayment(BankPay bankPay) throws StakeholderException, PaymentException {
        int money = bankPay.money();
        Customer customer = customerService.getCustomer(bankPay.customerId());
        Merchant merchant = merchantService.getMerchant(bankPay.merchantId());

        try {
            bankService.transferMoneyFromTo(customer.getBankAccount(), merchant.getBankAccount(), BigDecimal.valueOf(money), "Random Reason");
        } catch (BankServiceException_Exception e) {
            e.printStackTrace();
            throw new PaymentException("Payment failed because of: " + e.getMessage());
        }

        Payment payment = new Payment();
        payment.setAmount(money);

        payment.setCustomerId(customer.getStakeholderId());
        payment.setMerchantId(merchant.getStakeholderId());

        payments.add(payment);  // Adds the new payment to the list
    }
}
