package dk.dtu.grp08.customer.domain.exceptions;

public class NoSuchUserAccountException extends Exception {

    /**
     *
     * @author Esben
     */
    public NoSuchUserAccountException() {
        super("No such user account");
    }
}
