package dk.dtu.grp08.token.domain.exceptions;

/**
 * @author Clair Norah Mutebi (s184187)
 */
public class InvalidTokenException extends TokenException {
    public InvalidTokenException() {
        super("Invalid token");
    }
}
