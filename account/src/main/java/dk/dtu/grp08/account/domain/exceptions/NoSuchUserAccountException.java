package dk.dtu.grp08.account.domain.exceptions;

public class NoSuchUserAccountException extends UserAccountException {
    public NoSuchUserAccountException(String message) {
        super(message);
    }
}
