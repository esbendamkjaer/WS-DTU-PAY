package dk.dtu.grp08.payment.domain.exceptions;

public class NoSuchDebtorAccountException extends PaymentException {
    public NoSuchDebtorAccountException() {
        super("No such debtor account");
    }
}
