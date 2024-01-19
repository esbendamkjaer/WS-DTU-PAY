package dk.dtu.grp08.merchant.domain.exceptions;

/**
 * @author Muhamad Hussein Nadali (s233479)
 */
public class NoSuchUserAccountException extends Exception {
    public NoSuchUserAccountException() {
        super("No such user account");
    }
}
