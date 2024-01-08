package dk.dtu.grp08.dtupay.contracts;

import dk.dtu.grp08.dtupay.models.Merchant;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/merchants")
public interface IMerchantResource {

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    void createMerchant(Merchant merchant);

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    List<Merchant> listMerchants();

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    Merchant getMerchant(@PathParam("id") String id);

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    void updateMerchant(@PathParam("id") String id, Merchant merchant);


    @DELETE
    @Path("/{id}")
    void deleteMerchant(@PathParam("id") String id);

}
