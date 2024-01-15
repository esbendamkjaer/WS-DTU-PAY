package dk.dtu.grp08.customer.presentation.contracts;

import dk.dtu.grp08.customer.presentation.models.Token;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.UUID;

@Path("/tokens")
public interface ITokenAPI {

    @GET
    @Path("/{userId}")
    List<Token> getTokens(
        @QueryParam("count") int count,
        @PathParam("userId") UUID userId
    );

}
