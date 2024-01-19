package dk.dtu.grp08.account.domain.exceptions;

/**
 * @author Esben Damkjær Sørensen (s233474)
 */
public class NoSuchUserAccountException extends UserAccountException {
    public NoSuchUserAccountException(String message) {
        super(message);
    }
}
