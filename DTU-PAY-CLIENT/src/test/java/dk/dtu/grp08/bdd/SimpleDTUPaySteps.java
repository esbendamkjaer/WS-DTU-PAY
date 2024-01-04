package dk.dtu.grp08.bdd;

import dk.dtu.grp08.SimpleDTUPay;
import dk.dtu.grp08.models.Customer;
import dk.dtu.grp08.models.Merchant;
import dk.dtu.grp08.models.Payment;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.CucumberOptions;
import org.apache.http.util.Asserts;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@CucumberOptions(
    features = {"classpath:features/Payment.feature"},
    glue = {"dk.dtu.grp08.bdd"}
)
public class SimpleDTUPaySteps {

    private Merchant merchant;
    private Customer customer;

    private List<Payment> payments;

    String errorMessage;
    SimpleDTUPay dtuPay = new SimpleDTUPay();



    boolean successful;

    @Given("a customer with id {string}")
    public void aCustomerWithId(String cid) {
        customer = new Customer(cid);
        this.dtuPay.getCustomerResource().createCustomer(
            customer
        );
    }

    @Given("a merchant with id {string}")
    public void aMerchantWithId(String mid) {
        this.merchant = new Merchant(mid);
        this.dtuPay.getMerchantResource().createMerchant(
            this.merchant
        );
    }

    @When("the merchant initiates a payment for {int} kr by the customer")
    public void theMerchantInitiatesAPaymentForKrByTheCustomer(int amount) {
        successful = dtuPay.pay(
                amount,
                customer.getId(),
                merchant.getId()
        );
    }

    @When("the merchant initiates a payment for {int} kr by the customer with id {string}")
    public void theMerchantInitiatesAPaymentForKrByTheCustomer(int amount, String cid) {
        successful = dtuPay.pay(
                amount,
                cid,
                merchant.getId()
        );
    }

    @Then("the payment is successful")
    public void thePaymentIsSuccessful() {
        assertTrue(successful);
    }

    @Given("a successful payment of {int} kr from customer {string} to merchant {string}")
    public void aSuccessfulPayment(int amount, String cid, String mid){
        successful = dtuPay.pay(
            amount,
            cid,
            mid
        );
    }

    @When("the manager asks for a list of payments")
    public void ListOfPaymentsIsRequested(){
        this.payments = dtuPay.list();
    }

    @Then("the list contains a payments where customer {string} paid {int} kr to merchant {string}")
    public void theListContainsAPaymentsWhereDebtorPaidKrToCreditor(String cid, int amount, String mid){

        Asserts.notNull(payments.stream()
                .filter(
                    payment -> payment.getDebtor().equals(cid)
                            && payment.getCreditor().equals(mid)
                            && payment.getAmount() == amount
                ).findFirst()
                    .orElse(null),
    "Payment not found"
        );

    }

    @Then("the payment is not successful")
    public void thePaymentIsNotSuccessful() {
        assertFalse(successful);
    }

    @And("an error message is returned saying {string}")
    public void anErrorMessageIsReturnedSaying(String message) {
        assertEquals(message, errorMessage);
    }

}