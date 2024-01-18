package dk.dtu.grp08.customer.presentation.mappers;

import dk.dtu.grp08.customer.domain.exceptions.TokenException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class TokenExceptionMapper implements ExceptionMapper<TokenException> {
    @Override
    public Response toResponse(TokenException exception) {
        return Response.status(
            Response.Status.BAD_REQUEST
        ).entity(
            exception.getMessage()
        ).build();
    }
}
