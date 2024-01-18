package dk.dtu.grp08.merchant.domain.exceptions;

public class PaymentFailedException extends Exception {
    public PaymentFailedException(String message) {
        super(message);
    }
}
