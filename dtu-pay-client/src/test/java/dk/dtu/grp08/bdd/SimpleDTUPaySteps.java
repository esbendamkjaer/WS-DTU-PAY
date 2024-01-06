package dk.dtu.grp08.bdd;

import dk.dtu.grp08.SimpleDTUPay;
import dk.dtu.grp08.models.Customer;
import dk.dtu.grp08.models.Merchant;
import dk.dtu.grp08.models.Payment;
import io.cucumber.java.After;
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

    private Merchant merchant;
    private Customer customer;

    private List<Payment> payments;

    ClientErrorException errorMessage;
    SimpleDTUPay dtuPay = new SimpleDTUPay();

    boolean successful;

    @Given(("a customer with a bank account with balance {int}"))
    public void aCustomerWithABankAccountWithBalance(Integer balance){
        String accountId = dtuPay.registerBankAccount(
            "Customer",
            "Customer",
            "7554114334",
            BigDecimal.valueOf(balance)
        );

        this.customer = new Customer("cid1");
        this.customer.setAccountId(accountId);
    }

    @And("that the customer is registered with DTU Pay")
    public void thatTheCustomerIsRegisteredWithDtuPay() {
        this.dtuPay.getCustomerResource().createCustomer(
            this.customer
        );
    }

    @Given("a merchant with a bank account with balance {}")
    public void aMerchantWithABankAccountWithBalance(Integer balance) {
        String accountId = dtuPay.registerBankAccount("Merchant",
                "Merchant",
                "9554114334",
                BigDecimal.valueOf(balance)
        );

        this.merchant = new Merchant("mid1");
        this.merchant.setAccountId(accountId);
    }

    @And("that the merchant is registered with DTU Pay")
    public void thatTheMerchantIsRegisteredWithDtuPay() {
        this.dtuPay.getMerchantResource().createMerchant(
            this.merchant
        );
    }

    @And("the balance of the customer at the bank is {int} kr")
    public void theBalanceOfTheCustomerAtTheBankIsKr(Integer balance) {
        assertEquals(
            this.dtuPay.getBalance(customer.getAccountId()),
            balance
        );
    }

    @And("the balance of the merchant at the bank is {int} kr")
    public void theBalanceOfTheMerchantAtTheBankIsKr(Integer balance) {
            assertEquals(
                this.dtuPay.getBalance(merchant.getAccountId()),
                 balance
            );

    }

    @Given("a customer with id {string}")
    public void aCustomerWithId(String cid) {
        String accountId = dtuPay.registerBankAccount(
            "Customer",
            "Customer",
            "7554114334",
            BigDecimal.valueOf(1000)
        );

        customer = new Customer(cid);
        customer.setAccountId(accountId);
        this.dtuPay.getCustomerResource().createCustomer(
            customer
        );
    }

    @Given("a merchant with id {string}")
    public void aMerchantWithId(String mid) {
        String accountId = dtuPay.registerBankAccount(
            "Merchant",
            "Merchant",
            "7534004552",
            BigDecimal.valueOf(1000)
        );

        this.merchant = new Merchant(mid);
        this.merchant.setAccountId(accountId);
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

    @After
    public void cleanUp() {
        if (customer != null) {
            dtuPay.retireAccount(customer.getAccountId());
        }

        if (merchant != null) {
            dtuPay.retireAccount(merchant.getAccountId());
        }
    }

}