package dk.dtu.grp08.payment.domain.exceptions;

public class PaymentException extends RuntimeException {

    public PaymentException(String message) {
        super(message);
    }

}
