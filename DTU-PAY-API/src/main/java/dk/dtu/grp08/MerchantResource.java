package dk.dtu.grp08;

import dk.dtu.grp08.models.Merchant;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/merchants")
public class MerchantResource {
    private final Map<Merchant> merchants = new HashMap();

    @POST
    public void createMerchant(Merchant merchant) {
        this.merchants.put(merchant.getId(), merchant);
    }

    @GET
    public List<Merchant> listMerchants() {
        return this.merchants;
    }

    @GET
    @Path("/{id}")
    public Merchant getMerchant(@PathParam("id") String id) {
        return merchants.stream()
            .filter(
                merchant -> merchant.getId().equals(id)
            )
            .findFirst()
            .orElse(null);
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Merchant updateMerchant(@PathParam("id") String id, Merchant merchant) {

    }

}
