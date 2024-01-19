package dk.dtu.grp08.account.domain.exceptions;

/**
 * @author Esben
 */
public class NoSuchUserAccountException extends UserAccountException {
    public NoSuchUserAccountException(String message) {
        super(message);
    }
}
