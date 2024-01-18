package dk.dtu.grp08.manager.presentation.contracts;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/manager")
public interface IManagerFacade {

    @GET
    @Path("/reports")
    void getReports();
}
