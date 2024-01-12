package dk.dtu.grp08.token.presentation.resources;


import dk.dtu.grp08.token.domain.models.Token;
import dk.dtu.grp08.token.domain.models.UserId;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

import java.util.List;
import java.util.UUID;

@Path("/tokens")
public interface ITokenResource {


    @GET
    @Path("/{count}/{userId}")
    List<Token> getTokens(@PathParam("count") int count, @PathParam("userId") UUID userId);

}
