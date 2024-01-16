package dk.dtu.grp08.reporting.presentation.contracts;


import dk.dtu.grp08.reporting.domain.models.Token;
import dk.dtu.grp08.reporting.domain.models.payment.Payment;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.UUID;

@Path("/report")
public interface IReportResource {

    @POST
    @Path("/customer")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    List<Payment> getReportCustomer(Token token);


    @POST
    @Path("/merchant")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    List<Payment> getReportMerchant(UUID merchantID);


    @GET
    @Path("/manager")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    List<Payment> getReportManager();

}

