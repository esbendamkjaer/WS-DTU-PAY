package dk.dtu.grp08.payment.domain.exceptions;

/**
 * @author Clair Norah Mutebi (s184187)
 */
public class InsufficientAccountBalanceException extends PaymentException {
    public InsufficientAccountBalanceException() {
        super("Insufficient account balance");
    }
}
