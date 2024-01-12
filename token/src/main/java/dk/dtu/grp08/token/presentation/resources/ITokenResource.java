package dk.dtu.grp08.token.presentation.resources;


import dk.dtu.grp08.token.domain.models.Token;
import dk.dtu.grp08.token.domain.models.UserId;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

import java.util.List;
import java.util.UUID;

@Path("/tokens")
public interface ITokenResource {


    @GET
    @Path("/{count}/{userId}")
    @Operation(summary = "Get a number of tokens")
    List<Token> getTokens(
        @Parameter(description = "The number of tokens to get")
        @PathParam("count") int count,
        @Parameter(description = "The user to get the tokens for")
        @PathParam("userId") UUID userId
    );

}
