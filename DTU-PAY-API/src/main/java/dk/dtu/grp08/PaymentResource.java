package dk.dtu.grp08;

import dk.dtu.grp08.contracts.IPaymentResource;
import dk.dtu.grp08.models.Payment;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;

import java.util.ArrayList;
import java.util.List;

@Path("/payments")
public class PaymentResource implements IPaymentResource {
    public List<Payment> listPayments = new ArrayList<>();

    @Inject
    public MerchantResource merchantResource;

    @Inject
    public CustomerResource customerResource;


    @Override
    public boolean createPayment(Payment payment) {
        if (merchantResource.getMerchant(payment.getCreditor()) == null) {
            throw new NotFoundException(String.format("customer with id %s is unknown", payment.getCreditor()));
        }

        if (merchantResource.getMerchant(payment.getCreditor()) == null) {
            throw new NotFoundException(String.format("customer with id %s is unknown", payment.getDebtor()));
        }

        //TODO
        return listPayments.add(payment);
    }

    @Override
    public List<Payment> listPayments() {
        return listPayments;
    }


}

