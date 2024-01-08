package dk.dtu.grp08.dtupay.contracts;

import dk.dtu.grp08.dtupay.models.Customer;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/customers")
public interface ICustomerResource {

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    void createCustomer(Customer customer);


    @GET
    @Produces({MediaType.APPLICATION_JSON})
    List<Customer> getCustomers();

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    Customer getCustomer(@PathParam("id") String id);

    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    void updateCustomer(@PathParam("id") String id, Customer customer);

    @DELETE
    @Path("/{id}")
    void deleteCustomer(@PathParam("id") String id);

}