package org.acme.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.BankServiceService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
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
    public void setPayment(BankPay bankPay) throws BankServiceException_Exception {
        int money = bankPay.money();

        Customer customer = customerService.getCustomer(bankPay.customerId());
        Merchant merchant = merchantService.getMerchant(bankPay.merchantId());

        bankService.transferMoneyFromTo(customer.getBankAccount().getBankAccountId(), merchant.getBankAccount().getBankAccountId(), BigDecimal.valueOf(money), "Random Reason");
        //add try-catch

        Payment payment = new Payment();
        payment.setAmount(money);

        payment.setCustomerId(customer.getStakeholderId().getId());
        payment.setMerchantId(merchant.getStakeholderId().getId());

        payments.add(payment);  // Adds the new payment to the list
    }
}
