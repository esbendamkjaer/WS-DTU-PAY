package dk.dtu.grp08.models;

import java.util.Date;
import java.util.UUID;

public class Payment {
    private final String id;

    private final String debtor;

    private final String creditor;

    private final double amount;

    private final Date date;

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

}
