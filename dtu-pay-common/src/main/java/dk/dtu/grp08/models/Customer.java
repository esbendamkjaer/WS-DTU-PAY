package dk.dtu.grp08.models;

public class Customer extends Account {
    public Customer() {
        super();
    }

    String firstName;
    String lastName;
    String cprNumber;

    public Customer(String id) {
        super(id);
    }

}
