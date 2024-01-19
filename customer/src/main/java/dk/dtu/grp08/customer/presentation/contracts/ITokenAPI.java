package dk.dtu.grp08.customer.presentation.contracts;

import dk.dtu.grp08.customer.domain.models.Token;
import jakarta.ws.rs.*;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;
import java.util.UUID;

@Path("/tokens")
@RegisterRestClient(baseUri = "http://localhost:8084")
public interface ITokenAPI {

    /**
     *
     * @author Dilara
     */
    @GET
    @Path("/{userId}")
    List<Token> getTokens(
        @QueryParam("count") int count,
        @PathParam("userId") UUID userId
    );

}
