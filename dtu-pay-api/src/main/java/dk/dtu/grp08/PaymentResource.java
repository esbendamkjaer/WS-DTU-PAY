package dk.dtu.grp08;

import dk.dtu.grp08.contracts.IPaymentResource;
import dk.dtu.grp08.dtupay.bank.BankService;
import dk.dtu.grp08.dtupay.bank.BankServiceException_Exception;
import dk.dtu.grp08.dtupay.bank.BankServiceService;
import dk.dtu.grp08.models.Customer;
import dk.dtu.grp08.models.Merchant;
import dk.dtu.grp08.models.Payment;
import jakarta.inject.Inject;
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
        Merchant merchant = merchantResource.getMerchant(payment.getCreditor());
        Customer customer = customerResource.getCustomer(payment.getDebtor());

        BankService bank = new BankServiceService().getBankServicePort();

        try {
            bank.transferMoneyFromTo(customer.getAccountId(), merchant.getAccountId(), payment.getAmount(),"Payment");
        } catch (BankServiceException_Exception e) {
            throw new RuntimeException(e);
        }


        //TODO
        return listPayments.add(payment);
    }

    @Override
    public List<Payment> listPayments() {
        return listPayments;
    }


}

