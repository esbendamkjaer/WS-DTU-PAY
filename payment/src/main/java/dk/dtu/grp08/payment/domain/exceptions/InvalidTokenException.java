package dk.dtu.grp08.payment.domain.exceptions;

/**
 * @author Muhamad Hussein Nadali (s233479)
 */
public class InvalidTokenException extends PaymentException {

    public InvalidTokenException() {
        super("Invalid token");
    }

}
