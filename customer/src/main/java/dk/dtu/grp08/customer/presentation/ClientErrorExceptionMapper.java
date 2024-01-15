package dk.dtu.grp08.customer.presentation;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ClientErrorExceptionMapper implements ExceptionMapper<CustomerFacadeException> {

    @Override
    public Response toResponse(CustomerFacadeException e) {
        return Response
            .status(e.getStatus())
            .entity(e.getEntity())
            .build();
    }
}
