package dk.dtu.grp08.payment.domain.exceptions;

/**
 * @author Fuad Hassan Jama (s233468)
 */
public class PaymentException extends RuntimeException {

    public PaymentException(String message) {
        super(message);
    }

}
