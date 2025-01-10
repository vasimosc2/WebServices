package org.acme.services;

import java.util.ArrayList;
import java.util.List;

import org.acme.models.Payment;

public class PaymentService {

    // List to store all payment objects
    private List<Payment> payments = new ArrayList<>();

    // Returns the list of all payments
    public List<Payment> getPayments() {
        return payments;
    }

    // Adds a new payment to the list
    public void setPayment(Payment given_payment) {
        Payment newPayment = new Payment();
        newPayment.setAmount(given_payment.getAmount());
        newPayment.setCustomerId(given_payment.getCustomerId());
        newPayment.setMerchantId(given_payment.getMerchantId());       
        payments.add(newPayment);  // Adds the new payment to the list
    }
}
