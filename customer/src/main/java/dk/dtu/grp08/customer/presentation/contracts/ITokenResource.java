package dk.dtu.grp08.customer.presentation.contracts;

import dk.dtu.grp08.customer.presentation.models.Token;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;

import java.util.List;
import java.util.UUID;

@Path("/tokens")
public interface ITokenResource {

    @GET
    @Path("/{userId}")
    List<Token> getTokens(
        @QueryParam("count") int count,
        @PathParam("userId") UUID userId
    );

}
