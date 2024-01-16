package dk.dtu.grp08.payment.domain.exceptions;

public class InsufficientAccountBalanceException extends PaymentException {
    public InsufficientAccountBalanceException() {
        super("Insufficient account balance");
    }
}
