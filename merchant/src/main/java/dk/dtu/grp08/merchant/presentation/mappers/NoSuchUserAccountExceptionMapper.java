package dk.dtu.grp08.merchant.presentation.mappers;

import dk.dtu.grp08.merchant.domain.exceptions.NoSuchUserAccountException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 * @author Esben Damkjær Sørensen (s233474)
 */
@Provider
public class NoSuchUserAccountExceptionMapper implements ExceptionMapper<NoSuchUserAccountException> {

    @Override
    public Response toResponse(NoSuchUserAccountException e) {
        return Response
            .status(Response.Status.NOT_FOUND)
            .entity(e.getMessage())
            .build();
    }
}
