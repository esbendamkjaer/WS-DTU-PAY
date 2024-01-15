package dk.dtu.grp08.customer.presentation.contracts;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

@Path("/customer")
public interface ICustomerFacade {

    @Produces({MediaType.APPLICATION_JSON})
    @GET
    @Operation(summary = "Get all user accounts", description = "Get all user accounts")
    @APIResponse(responseCode = "200", description = "User accounts retrieved successfully")
    @APIResponse(responseCode = "500", description = "Internal server error")
    @Path("/register")
    void register();
    @Produces({MediaType.APPLICATION_JSON})
    @GET
    @Operation(summary = "Get all user accounts", description = "Get all user accounts")
    @APIResponse(responseCode = "200", description = "User accounts retrieved successfully")
    @APIResponse(responseCode = "500", description = "Internal server error")
    void deregister();
    @Produces({MediaType.APPLICATION_JSON})
    @GET
    @Operation(summary = "Get all user accounts", description = "Get all user accounts")
    @APIResponse(responseCode = "200", description = "User accounts retrieved successfully")
    @APIResponse(responseCode = "500", description = "Internal server error")
    void getTokens();
    @Produces({MediaType.APPLICATION_JSON})
    @GET
    @Operation(summary = "Get all user accounts", description = "Get all user accounts")
    @APIResponse(responseCode = "200", description = "User accounts retrieved successfully")
    @APIResponse(responseCode = "500", description = "Internal server error")
    void getReport();
}
