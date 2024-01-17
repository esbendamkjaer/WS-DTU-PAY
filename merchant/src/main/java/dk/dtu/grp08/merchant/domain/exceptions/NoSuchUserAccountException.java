package dk.dtu.grp08.merchant.domain.exceptions;

public class NoSuchUserAccountException extends Exception {
    public NoSuchUserAccountException() {
        super("No such user account");
    }
}
