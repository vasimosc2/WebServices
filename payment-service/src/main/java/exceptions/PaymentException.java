package exceptions;

public class PaymentException extends Exception {

    public PaymentException(String errorMessage) {
        super(errorMessage);
    }
}