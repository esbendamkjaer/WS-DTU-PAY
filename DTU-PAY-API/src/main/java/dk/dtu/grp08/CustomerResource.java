package dk.dtu.grp08;

import dk.dtu.grp08.contracts.ICustomerResource;
import dk.dtu.grp08.models.Customer;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;

import java.util.ArrayList;
import java.util.List;

@Path("/customers")
public class CustomerResource implements ICustomerResource {
    public List<Customer> customers = new ArrayList<>();

    @Override
    public void createCustomer(Customer customer) {
        this.customers.add(customer);
    }

    @Override
    public List<Customer> getCustomers() {
        return customers;
    }

    @Override
    public Customer getCustomer(String id) {
        return customers.stream()
            .filter(
                    customer -> customer.getId().equals(id)
            )
            .findFirst()
            .orElseThrow(() -> new NotFoundException("Customer with id " + id + " is unknown"));
    }

    @Override
    public void updateCustomer(String id, Customer customer) {
        // @TODO
        return;
    }

    @Override
    public void deleteCustomer(String id) {
        customers.removeIf(customer -> customer.getId().equals(id));
    }

}
