package dk.dtu.grp08.models;

import java.util.Date;
import java.util.UUID;

public class Payment {
    private String id;
    private String debtor;
    private String creditor;
    private double amount;
    private Date date;

    public Payment() {

    }

    public Payment(
        String debtor,
        String creditor,
        double amount
    ) {
        this.debtor = debtor;
        this.creditor = creditor;
        this.amount = amount;

        this.id = String.valueOf(UUID.randomUUID());
        this.date = new Date();
    }

    public String getDebtor() {
        return this.debtor;
    }

    public String getCreditor() {
        return this.creditor;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCreditor(String creditor) {
        this.creditor = creditor;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDebtor(String debtor) {
        this.debtor = debtor;
    }
}
