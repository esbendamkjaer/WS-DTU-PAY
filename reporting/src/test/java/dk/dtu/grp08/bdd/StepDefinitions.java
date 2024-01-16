package dk.dtu.grp08.bdd;

import dk.dtu.grp08.adapter.BankAdapter;
import dk.dtu.grp08.adapter.IBankAdapter;
import dk.dtu.grp08.dtupay.customer.CustomerFacade;
import dk.dtu.grp08.dtupay.customer.ICustomerFacade;
import dk.dtu.grp08.dtupay.merchant.IMerchantFacade;
import dk.dtu.grp08.dtupay.merchant.MerchantFacade;
import dk.dtu.grp08.dtupay.models.PaymentRequest;
import dk.dtu.grp08.dtupay.models.Token;
import dk.dtu.grp08.reporting.domain.events.EventType;
import dk.dtu.grp08.reporting.domain.events.PaymentTransferEvent;
import dk.dtu.grp08.reporting.domain.models.user.UserAccount;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import messaging.Event;
import org.junit.Assert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class StepDefinitions {


    private final List<Token> customerTokens = new ArrayList<>();
    private final IBankAdapter bankAdapter = new BankAdapter();
    private final IMerchantFacade merchantFacade = new MerchantFacade();
    private final ICustomerFacade customerFacade = new CustomerFacade();
    private UserAccount customer;
    private UserAccount merchant;
    private Transaction paymentTransferredEvent;
    private Report customerReport;
    private Report merchantReport;

    private PaymentRequest paymentRequest;

    EventType eventType = EventType.PAYMENT_TRANSFERRED;

    @When("A \"PAYMENT_TRANSFERRED\" event")
    public void aPaymentTransferredEvent(double amount) {

        PaymentTransferEvent paymentTransferEvent = new PaymentTransferEvent();
        paymentTransferEvent.setMerchantID(merchant.getUserId()
        paymentTransferEvent.setToken(customerTokens.get(0));
        paymentTransferEvent.setAmount(BigDecimal.valueOf(amount));

        eventType.PAYMENT_TRANSFERRED(new Event(paymentTransferEvent));
    }

    @When("the customer requests a report")
    public void theCustomerRequestsAReport() {
        customerReport = customerFacade.requestReport(customer.getUserId());
    }

    @Then("the customer should see a report with the following transaction details")
    public void theCustomerShouldSeeAReportWithTheFollowingTransactionDetails() {

        Assert.assertTrue(customerReport.containsTransaction(paymentTransferredEvent));
    }

    @When("the merchant requests a report")
    public void theMerchantRequestsAReport() {
        merchantReport = merchantFacade.requestReport(merchant.getUserId());
    }

    @Then("the merchant should see a report with the following transaction details")
    public void theMerchantShouldSeeAReportWithTheFollowingTransactionDetails() {

        Assert.assertTrue(merchantReport.containsTransaction(paymentTransferredEvent));
    }

    class Transaction {

    }

    class Report {

        boolean containsTransaction(Transaction transaction) {

            return true;
        }
    }
}
