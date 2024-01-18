package dk.dtu.grp08.manager.presentation.contracts;

import dk.dtu.grp08.manager.domain.models.Payment;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Path("/manager")
public interface IManagerFacade {

    @GET
    @Path("/report")
    @Produces({MediaType.APPLICATION_JSON})
    CompletableFuture<List<Payment>> getReport();
}
