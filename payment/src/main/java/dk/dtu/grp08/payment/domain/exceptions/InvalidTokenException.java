package dk.dtu.grp08.payment.domain.exceptions;

public class InvalidTokenException extends PaymentException {

    public InvalidTokenException() {
        super("Invalid token");
    }

}
