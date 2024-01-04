package dk.dtu.grp08.models;

import java.util.UUID;

public class Account {
    private final String id;
    private double balance = 0.00;

    public Account(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

}
