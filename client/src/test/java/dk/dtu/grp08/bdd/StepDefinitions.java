package dk.dtu.grp08.bdd;

import dk.dtu.grp08.adapter.BankAdapter;
import dk.dtu.grp08.adapter.IBankAdapter;
import dk.dtu.grp08.dtupay.customer.CustomerFacade;
import dk.dtu.grp08.dtupay.customer.ICustomerFacade;
import dk.dtu.grp08.dtupay.merchant.IMerchantFacade;
import dk.dtu.grp08.dtupay.merchant.MerchantFacade;
import dk.dtu.grp08.stubs.account.models.user.BankAccountNo;
import dk.dtu.grp08.stubs.account.models.user.UserAccount;
import dk.dtu.grp08.stubs.payment.models.PaymentRequest;
import dk.dtu.grp08.stubs.token.models.Token;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.ws.rs.ClientErrorException;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertTrue;

public class StepDefinitions {

    private final IBankAdapter bankAdapter = new BankAdapter();
    private final IMerchantFacade merchantFacade = new MerchantFacade();
    private final ICustomerFacade customerFacade = new CustomerFacade();


    private final List<Token> customerTokens = new ArrayList<>();
    private UserAccount customer;
    private UserAccount merchant;
    private ClientErrorException exception;
    private PaymentRequest paymentRequest;


    @Given("a merchant named {string}")
    public void aMerchantWithName(String name) {
        this.merchant = new UserAccount();
        this.merchant.setName(name);
        this.merchant.setCpr(
            UUID.randomUUID().toString()
        );
    }

    @Given("a customer named {string}")
    public void aCustomerWithNameAndBankAccountDetails(String name) {
        this.customer = new UserAccount();
        this.customer.setName(name);
        this.customer.setCpr(
            UUID.randomUUID().toString()
        );
    }

    @And("the customer is registered with DTU Pay")
    public void theCustomerIsRegisteredInDTUPay() {
        this.customer = this.customerFacade.register(
            this.customer.getName(),
            this.customer.getCpr(),
            this.customer.getBankAccountNo()
        );
    }

    @And("the merchant is registered with DTU Pay")
    public void theMerchantIsRegisteredInDTUPay() {
        this.merchant = this.merchantFacade.register(
            this.merchant.getName(),
            this.merchant.getCpr(),
            this.merchant.getBankAccountNo()
        );
    }

    @And("the customer has a bank account with balance {double}")
    public void theCustomerHasABankAccount(double balance) {
        BankAccountNo bankAccountNo = this.bankAdapter.createBankAccount(
            this.customer.getName(),
    "Some last name",
            this.customer.getCpr(),
            BigDecimal.valueOf(balance)
        );

        this.customer.setBankAccountNo(bankAccountNo);
    }

    @And("the merchant has a bank account with balance {double}")
    public void theMerchantHasABankAccount(double balance) {
        BankAccountNo bankAccountNo = this.bankAdapter.createBankAccount(
            this.merchant.getName(),
    "Some last name",
            this.merchant.getCpr(),
            BigDecimal.valueOf(balance)
        );

        this.merchant.setBankAccountNo(bankAccountNo);
    }

    @When("the customer gets {int} tokens")
    public void theCustomerHasTokens(int count) {
        try {
            this.customerTokens.addAll(
                this.customerFacade.getTokens(
                    this.customer.getId(),
                    count
                )
            );
        } catch (ClientErrorException e) {
            this.exception = e;
        }
    }

    @When(("the merchant requests a payment of {double} kr"))
    public void theMerchantRequestsAPayment(double amount) {
        this.paymentRequest = new PaymentRequest();
        paymentRequest.setAmount(
            BigDecimal.valueOf(amount)
        );
        paymentRequest.setMerchantId(
            this.merchant.getId().getId()
        );
    }

    @And("the customer grants the payment with a token")
    public void theCustomerGrantsThePaymentWithAToken() {
        this.paymentRequest.setToken(
            this.customerTokens.removeLast()
        );

        this.merchantFacade.pay(
            this.paymentRequest
        );
    }

    @Then("the customer has {int} unused tokens")
    public void theCustomerHasUnusedTokens(int count) {
        Assert.assertEquals(
            count,
            this.customerTokens.size()
        );
    }

    @When("the token has to be validated")
    public  void theTokenHasToBeValidated(){
        //TODO
    }

    @Then("the error with message {string} is received")
    public void anErrorMessageIsReceived(String message) {
        Assertions.assertEquals(
            message,
            exception
                .getResponse()
                .readEntity(String.class)
        );
    }

    @Then("the token is not validated")
    public void theTokenIsNotValidated(){
        assertTrue(exception.getMessage().contains("Token is not validated"));
    }

    @Given("a token is not in the list")
    public void aTokenIsNotInTheList(){
        //TODO
    }

    @Then("the customer is assigned an id")
    public void theCustomerIsAssignedAnId(){
        Assert.assertNotNull(
            this.customer.getId()
        );
    }

    @Then("the merchant is assigned an id")
    public void theMerchantIsAssignedAnId(){
        Assert.assertNotNull(
            this.merchant.getId()
        );
    }

    @Then("the merchant has balance {double}")
    public void theMerchantHasBalance(double balance) {
        BigDecimal merchantBalance = this.bankAdapter.getBalance(
            this.merchant.getBankAccountNo()
        );

        Assert.assertEquals(
            BigDecimal.valueOf(balance),
            merchantBalance
        );
    }

    @Then("the customer has balance {double}")
    public void theCustomerHasBalance(double balance) {
        BigDecimal customerBalance = this.bankAdapter.getBalance(
            this.customer.getBankAccountNo()
        );

        Assert.assertEquals(
            BigDecimal.valueOf(balance),
            customerBalance
        );
    }

    @After
    public void cleanUp() {
        if (this.customer != null) {
            this.bankAdapter.retireBankAccount(
                this.customer.getBankAccountNo()
            );

            this.customerFacade.deregister(
                this.customer.getId()
            );
        }

        if (this.merchant != null) {
            this.bankAdapter.retireBankAccount(
                this.merchant.getBankAccountNo()
            );

            this.merchantFacade.deregister(
                this.merchant.getId()
            );
        }
    }


}

