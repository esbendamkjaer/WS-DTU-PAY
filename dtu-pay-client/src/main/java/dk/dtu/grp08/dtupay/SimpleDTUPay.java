package dk.dtu.grp08.dtupay;

import dk.dtu.grp08.bank.BankService;
import dk.dtu.grp08.bank.BankServiceException_Exception;
import dk.dtu.grp08.bank.BankServiceService;
import dk.dtu.grp08.bank.User;
import dk.dtu.grp08.dtupay.contracts.ICustomerResource;
import dk.dtu.grp08.dtupay.contracts.IMerchantResource;
import dk.dtu.grp08.dtupay.contracts.IPaymentResource;
import dk.dtu.grp08.dtupay.models.Payment;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import java.math.BigDecimal;
import java.util.List;

public class SimpleDTUPay {
    private IPaymentResource paymentResource;
    private ICustomerResource customerResource;
    private IMerchantResource merchantResource;

    private BankService bankService;

    public SimpleDTUPay() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080");
        ResteasyWebTarget rtarget = (ResteasyWebTarget) target;
        bankService = new BankServiceService().getBankServicePort();

        this.paymentResource = rtarget.proxy(IPaymentResource.class);
        this.customerResource = rtarget.proxy(ICustomerResource.class);
        this.merchantResource = rtarget.proxy(IMerchantResource.class);
    }


    public String registerUserAccount(User user, String bankAccountNo){

        User user1 = new User();
        user.setLastName(bankAccountNo);
        return null;
    }

    public String registerBankAccount(String lastName, String firstName, String cprNumber, BigDecimal amount) {
        User user = new User();
        user.setLastName(lastName);
        user.setFirstName(firstName);
        user.setCprNumber(cprNumber);
        try {
            return bankService.createAccountWithBalance(user, amount);
        } catch (BankServiceException_Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean pay(BigDecimal amount, String cid, String mid) throws NotFoundException {
        return paymentResource.createPayment(
            new Payment(cid,mid,amount)
        );
    }

    public void retireAccount(String accountId) {
        try {
            bankService.retireAccount(accountId);
        } catch (BankServiceException_Exception e) {
            e.printStackTrace();
        }
    }

    public int getBalance(String accountID){
        try {
            return  bankService.getAccount(accountID).getBalance().intValue();
        } catch (BankServiceException_Exception e) {
            throw new RuntimeException(e);
        }
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

    public BankService getBankService() {
        return bankService;
    }
}