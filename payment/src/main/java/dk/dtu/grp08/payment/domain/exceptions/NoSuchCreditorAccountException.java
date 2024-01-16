package dk.dtu.grp08.payment.domain.exceptions;

public class NoSuchCreditorAccountException extends PaymentException {
    public NoSuchCreditorAccountException() {
        super("No such creditor account");
    }
}
