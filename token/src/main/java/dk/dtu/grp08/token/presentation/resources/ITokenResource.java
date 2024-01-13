package dk.dtu.grp08.token.presentation.resources;

import dk.dtu.grp08.token.domain.exceptions.InvalidTokenException;
import dk.dtu.grp08.token.domain.exceptions.TokenException;
import dk.dtu.grp08.token.domain.models.Token;
import dk.dtu.grp08.token.domain.models.UserId;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import java.util.List;
import java.util.UUID;
@Path("/tokens")
public interface ITokenResource {

    @GET
    @Path("/{count}/{userId}")
    @Operation(summary = "Get a number of tokens",
            description = "Retrieves a specified number of tokens for a given user. " +
                    "The user must be registered in DTU Pay and have sufficient account balance.")
    @APIResponse(responseCode = "200", description = "Tokens retrieved successfully",
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(name = "responseExample",
                            value = "{\"tokens\":[{\"id\":\"token1\"},{\"id\":\"token2\"}]")))
    @APIResponse(
        responseCode = "400",
        description = "Bad Request - Error message is dynamically generated based on the specific request issue.",
        content = @Content(
            schema = @Schema(
                oneOf = {
                    TokenException.class,
                    InvalidTokenException.class
                }
            ),
            mediaType = "text/plain",
            examples = @ExampleObject(
                    name = "errorExample",
                    value = "User has more than one unused token"
            )
        )
    )
    @APIResponse(responseCode = "404", description = "User not found")
    @APIResponse(responseCode = "500", description = "Internal server error")
    List<Token> getTokens(
            @Parameter(description = "The number of tokens to get. For example, '5' to request five tokens.",
                    example = "5",
                    required = true)
            @PathParam("count") int count,
            @Parameter(description = "The unique identifier of the user to get the tokens for.",
                    example = "123e4567-e89b-12d3-a456-426614174000",
                    required = true)
            @PathParam("userId") UUID userId
    );

}
