package dk.dtu.grp08.merchant.domain.exceptions;
/**
 * @author Alexander Matzen (s233475)
 */
public class PaymentFailedException extends Exception {
    public PaymentFailedException(String message) {
        super(message);
    }
}
