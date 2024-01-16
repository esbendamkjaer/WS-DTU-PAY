package dk.dtu.grp08.customer.presentation.mappers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.resteasy.reactive.ClientWebApplicationException;

@Provider
public class ClientWebApplicationExceptionMapper implements ExceptionMapper<ClientWebApplicationException> {

    @Override
    public Response toResponse(ClientWebApplicationException e) {
        System.out.println("ClientWebApplicationExceptionMapper");
        return Response.status(
            e.getResponse().getStatus()
        ).entity(
            e.getResponse().readEntity(String.class)
        ).build();
    }
}
