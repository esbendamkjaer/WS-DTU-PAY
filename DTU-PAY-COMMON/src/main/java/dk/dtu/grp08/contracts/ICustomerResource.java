package dk.dtu.grp08.contracts;

import dk.dtu.grp08.models.Customer;
import jakarta.ws.rs.*;

import java.util.List;

@Path("/customers")
public interface ICustomerResource {


    @POST
    void createCustomer(Customer customer);

    @GET
    List<Customer> getCustomers();

    @GET
    @Path("/{id}")
    Customer getCustomer(@PathParam("id") String id);

    @PUT
    @Path("/{id}")
    void updateCustomer(@PathParam("id") String id, Customer customer);

    @DELETE
    @Path("/{id}")
    void deleteCustomer(@PathParam("id") String id);

}
