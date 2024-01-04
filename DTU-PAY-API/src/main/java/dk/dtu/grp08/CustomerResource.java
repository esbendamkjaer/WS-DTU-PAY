package dk.dtu.grp08;

import dk.dtu.grp08.models.Customer;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Path;

import java.util.ArrayList;
import java.util.List;

@Path("/customers")
public class CustomerResource {
    public List<Customer> customers = new ArrayList<>();

    @POST
    public void createCustomer(Customer customer) {
        this.customers.add(customer);
    }

    @GET
    public List<Customer> getCustomers() {
        return customers;
    }

    @GET
    @Path("/{id}")
    public Customer getCustomer(@PathParam("id") String id) {
        return customers.stream()
            .filter(
                    customer -> customer.getId().equals(id)
            )
            .findFirst()
            .orElse(null);
    }

    @PUT
    @Path("/{id}")
    public void updateCustomer(@PathParam("id") String id, Customer customer) {
        // @TODO
        return;
    }

    @DELETE
    @Path("/{id}")
    public void deleteCustomer(@PathParam("id") String id) {
        customers.removeIf(customer -> customer.getId().equals(id));
    }

}
