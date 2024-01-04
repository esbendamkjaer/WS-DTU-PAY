package dk.dtu.grp08;

import dk.dtu.grp08.models.Payment;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/payments")
public class PaymentResource {
    public List<Payment> listPayments;
    public


    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public boolean createPayment(Payment payment) {

        if(payment.getDebtor())
        //TODO
        return false;
    }


    public boolean checkIfIdExist(String id){

        boolean isExist = false;
        for (Payment payment : listPayments) {
            // code block to be executed
            if(payment.getCreditor() == id || payment.getDebtor() == id){
                isExist = true;
            }

        }

        return  isExist;


    }
}

