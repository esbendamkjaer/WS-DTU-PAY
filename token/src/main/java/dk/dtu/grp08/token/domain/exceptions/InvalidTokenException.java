package dk.dtu.grp08.token.domain.exceptions;

/**
 * @author Clair
 */
public class InvalidTokenException extends TokenException {
    public InvalidTokenException() {
        super("Invalid token");
    }
}
