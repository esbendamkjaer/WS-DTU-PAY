package dk.dtu.grp08.account.domain.exceptions;

/**
 * @author Fuad
 */
public class UserAccountException extends RuntimeException {
    public UserAccountException(String message) {
        super(message);
    }
}
