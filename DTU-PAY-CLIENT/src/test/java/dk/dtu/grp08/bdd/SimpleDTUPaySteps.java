package dk.dtu.grp08.bdd;

import dk.dtu.grp08.SimpleDTUPay;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.CucumberOptions;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@CucumberOptions(
    features = {"classpath:features/Payment.feature"},
    glue = {"dk.dtu.grp08.bdd"}
)
public class SimpleDTUPaySteps {
    String cid, mid;
    SimpleDTUPay dtuPay = new SimpleDTUPay();



    boolean successful;

    @Given("a customer with id {string}")
    public void aCustomerWithId(String cid) {
        this.cid = cid;
    }

    @Given("a merchant with id {string}")
    public void aMerchantWithId(String mid) {
        this.mid = mid;
    }

    @When("the merchant initiates a payment for {int} kr by the customer")
    public void theMerchantInitiatesAPaymentForKrByTheCustomer(int amount) {
        successful = dtuPay.pay(amount,cid,mid);
    }

    @Then("the payment is successful")
    public void thePaymentIsSuccessful() {
        assertTrue(successful);;
    }

    @Given("a successful payment of {int} kr from customer {string} to merchant {string}")
    public void aSuccessfulPayment(int amount){
        successful = dtuPay.pay(amount,cid,mid);
    }

    @When("list of Payments is requested")
    public void ListOfPaymentsIsRequested(){

    }

    @Then("the payment is not successful")
    public void thePaymentIsNotSuccessful() {
        assertFalse(successful);;
    }
}