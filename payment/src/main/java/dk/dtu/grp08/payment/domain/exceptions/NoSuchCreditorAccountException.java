package dk.dtu.grp08.payment.domain.exceptions;

/**
 * @author Alexander Matzen (s233475)
 */
public class NoSuchCreditorAccountException extends PaymentException {
    public NoSuchCreditorAccountException() {
        super("No such creditor account");
    }
}
