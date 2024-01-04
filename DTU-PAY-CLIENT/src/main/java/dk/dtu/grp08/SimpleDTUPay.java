package dk.dtu.grp08;

import dk.dtu.grp08.contracts.ICustomerResource;
import dk.dtu.grp08.contracts.IMerchantResource;
import dk.dtu.grp08.contracts.IPaymentResource;
import dk.dtu.grp08.models.Payment;
import jakarta.ws.rs.ClientErrorException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import java.util.List;

public class SimpleDTUPay {
    private IPaymentResource paymentResource;
    private ICustomerResource customerResource;
    private IMerchantResource merchantResource;

    public SimpleDTUPay() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080");
        ResteasyWebTarget rtarget = (ResteasyWebTarget) target;

        this.paymentResource = rtarget.proxy(IPaymentResource.class);
        this.customerResource = rtarget.proxy(ICustomerResource.class);
        this.merchantResource = rtarget.proxy(IMerchantResource.class);
    }

    public boolean pay(int amount, String cid, String mid) throws NotFoundException {
        return paymentResource.createPayment(
            new Payment(cid,mid,amount)
        );
    }

    public List<Payment> list(){
        return paymentResource.listPayments();

    }

    public IPaymentResource getPaymentResource() {
        return paymentResource;
    }

    public ICustomerResource getCustomerResource() {
        return customerResource;
    }

    public IMerchantResource getMerchantResource() {
        return merchantResource;
    }
}