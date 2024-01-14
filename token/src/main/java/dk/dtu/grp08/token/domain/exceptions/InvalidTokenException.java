package dk.dtu.grp08.token.domain.exceptions;

public class InvalidTokenException extends TokenException {
    public InvalidTokenException() {
        super("Invalid token");
    }
}
