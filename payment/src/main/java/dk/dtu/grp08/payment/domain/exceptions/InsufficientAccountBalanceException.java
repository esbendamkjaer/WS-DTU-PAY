package dk.dtu.grp08.payment.domain.exceptions;

/**
 * @author Clair
 */
public class InsufficientAccountBalanceException extends PaymentException {
    public InsufficientAccountBalanceException() {
        super("Insufficient account balance");
    }
}
