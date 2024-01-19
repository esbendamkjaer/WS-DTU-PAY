package dk.dtu.grp08.customer.presentation.mappers;

import dk.dtu.grp08.customer.domain.exceptions.NoSuchUserAccountException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NoSuchUserAccountExceptionMapper implements ExceptionMapper<NoSuchUserAccountException> {

    /**
     *
     * @author Muhamad
     */
    @Override
    public Response toResponse(NoSuchUserAccountException e) {
        return Response
            .status(Response.Status.NOT_FOUND)
            .entity(e.getMessage())
            .build();
    }
}
