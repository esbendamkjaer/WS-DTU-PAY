package dk.dtu.grp08;

import dk.dtu.grp08.contracts.IPaymentResource;
import dk.dtu.grp08.models.Customer;
import dk.dtu.grp08.models.Payment;

import java.util.List;

public class PaymentResource implements IPaymentResource {
    public List<Payment> listPayments;

    public MerchantResource merchantResource;
    public CustomerResource customerResource;


    @Override
    public boolean createPayment(Payment payment) {

        if(! (merchantResource.getMerchant(payment.getCreditor()) && customerResource.getCustomer(payment.getCreditor()) )){

            return false;
        }


        //TODO
        return false;
    }



}

