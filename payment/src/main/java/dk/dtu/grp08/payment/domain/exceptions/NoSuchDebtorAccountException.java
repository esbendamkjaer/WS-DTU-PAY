package dk.dtu.grp08.payment.domain.exceptions;

/**
 * @author Esben
 */
public class NoSuchDebtorAccountException extends PaymentException {
    public NoSuchDebtorAccountException() {
        super("No such debtor account");
    }
}
