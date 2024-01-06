package dk.dtu.grp08.bdd;

import dk.dtu.grp08.SimpleDTUPay;
//import dk.dtu.grp08.dtupay.bank.BankService;
//import dk.dtu.grp08.dtupay.bank.BankServiceService;
import dk.dtu.grp08.models.Customer;
import dk.dtu.grp08.models.Merchant;
import dk.dtu.grp08.models.Payment;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.CucumberOptions;
import jakarta.ws.rs.ClientErrorException;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@CucumberOptions(
    features = {"classpath:features/Payment.feature"},
    glue = {"dk.dtu.grp08.bdd"}
)
public class SimpleDTUPaySteps {

    //private BankService bank = new BankServiceService().getBankServicePort();

    private Merchant merchant;
    private Customer customer;

    private List<Payment> payments;

    ClientErrorException errorMessage;
    SimpleDTUPay dtuPay = new SimpleDTUPay();

    boolean successful;

    @Given(("a customer with a bank account with balance {}"))
    public void aCustomerWithABankAccountWithBalance(){

    }

    @And("that the customer is registered with DTU Pay")
    public void thatTheCustomerIsRegisteredWithDtuPay() {

    }

    @Given("a merchant with a bank account with balance {}")
    public void aMerchantWithABankAccountWithBalance() {}



    @And("that the merchant is registered with DTU Pay")
    public void thatTheMerchantIsRegisteredWithDtuPay() {

    }

    @And("the balance of the customer at the bank is {} kr")
    public void theBalanceOfTheCustomerAtTheBankIsKr() {

    }

    @And("the balance of the merchant at the bank is {} kr")
    public void theBalanceOfTheMerchantAtTheBankIsKr() {

    }

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

    @When("the merchant with id {string} initiates a payment for {int} kr by the customer")
    public void theMerchantInitiatesAPaymentForKrByTheCustomer(String mid, Integer amount) {
        try {
            successful = dtuPay.pay(
                BigDecimal.valueOf(amount),
                customer.getId(),
                mid
            );
        } catch (ClientErrorException e) {
            errorMessage = e;
        }
    }

    @When("the merchant initiates a payment for {int} kr by the customer")
    public void theMerchantInitiatesAPaymentForKrByTheCustomer(Integer amount) {
        successful = dtuPay.pay(
                BigDecimal.valueOf(amount),
                customer.getId(),
                merchant.getId()
        );
    }

    @When("the merchant initiates a payment for {int} kr by the customer with id {string}")
    public void theMerchantInitiatesAPaymentForKrByTheCustomer(Integer amount, String cid) {
        try {
            successful = dtuPay.pay(
                    BigDecimal.valueOf(amount),
                    cid,
                    merchant.getId()
            );
        } catch (ClientErrorException e) {
            errorMessage = e;
        }
    }

    @Then("the payment is successful")
    public void thePaymentIsSuccessful() {
        assertTrue(successful);
    }

    @Given("a successful payment of {int} kr from customer {string} to merchant {string}")
    public void aSuccessfulPayment(Integer amount, String cid, String mid){
        successful = dtuPay.pay(
            BigDecimal.valueOf(amount),
            cid,
            mid
        );
    }

    @When("the manager asks for a list of payments")
    public void ListOfPaymentsIsRequested(){
        this.payments = dtuPay.list();
    }

    @Then("the list contains a payments where customer {string} paid {int} kr to merchant {string}")
    public void theListContainsAPaymentsWhereDebtorPaidKrToCreditor(String cid, Integer amount, String mid){
        Assertions.assertNotNull(
            payments.stream()
                .filter(
                    payment -> payment.getDebtor().equals(cid)
                            && payment.getCreditor().equals(mid)
                            && payment.getAmount().intValue() == amount
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
        assertEquals(message, errorMessage.getResponse().readEntity(String.class));
    }

}