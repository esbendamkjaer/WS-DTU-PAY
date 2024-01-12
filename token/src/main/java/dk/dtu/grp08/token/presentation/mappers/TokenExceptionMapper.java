package dk.dtu.grp08.token.presentation.mappers;

import dk.dtu.grp08.token.domain.exceptions.TokenException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class TokenExceptionMapper implements ExceptionMapper<TokenException> {
    @Override
    public Response toResponse(TokenException e) {
        return Response
            .status(Response.Status.BAD_REQUEST)
            .entity(e.getMessage())
            .build();
    }
}
