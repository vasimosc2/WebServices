package org.acme;

import java.util.ArrayList;
import java.util.List;

public class PaymentService {

    // List to store all payment objects
    private List<Payment> payments = new ArrayList<>();

    // Returns the list of all payments
    public List<Payment> getPayments() {
        return payments;
    }

    // Adds a new payment to the list
    public void setPayment(Payment given_payment) {
        Payment newPayment = new Payment(
                given_payment.getAmount(),
                given_payment.getCustomerId(),
                given_payment.getMerchantId()
        );
        payments.add(newPayment);  // Adds the new payment to the list
    }
}
