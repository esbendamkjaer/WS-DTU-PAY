package dk.dtu.grp08.payment.domain.exceptions;

/**
 * @author Muhamad
 */
public class InvalidTokenException extends PaymentException {

    public InvalidTokenException() {
        super("Invalid token");
    }

}
