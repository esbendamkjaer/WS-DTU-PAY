package dk.dtu.grp08.payment.domain.exceptions;

/**
 * @author Fuad
 */
public class PaymentException extends RuntimeException {

    public PaymentException(String message) {
        super(message);
    }

}
