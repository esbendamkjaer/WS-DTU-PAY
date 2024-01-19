package dk.dtu.grp08.customer.domain.exceptions;

public class NoSuchUserAccountException extends Exception {

    /**
     *
     * @author Esben Damkjær Sørensen (s233474)
     */
    public NoSuchUserAccountException() {
        super("No such user account");
    }
}
