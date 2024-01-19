package dk.dtu.grp08.bdd;

import dk.dtu.grp08.adapter.BankAdapter;
import dk.dtu.grp08.adapter.IBankAdapter;
import dk.dtu.grp08.bank.BankServiceException_Exception;
import dk.dtu.grp08.dtupay.customer.CustomerFacade;
import dk.dtu.grp08.dtupay.customer.ICustomerFacade;
import dk.dtu.grp08.dtupay.manager.IManagerFacade;
import dk.dtu.grp08.dtupay.manager.ManagerFacade;
import dk.dtu.grp08.dtupay.merchant.IMerchantFacade;
import dk.dtu.grp08.dtupay.merchant.MerchantFacade;
import dk.dtu.grp08.dtupay.models.*;
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
import java.util.Objects;
import java.util.UUID;

import static org.junit.Assert.assertTrue;

public class StepDefinitions {

    private final IBankAdapter bankAdapter = new BankAdapter();
    private final IMerchantFacade merchantFacade = new MerchantFacade();
    private final ICustomerFacade customerFacade = new CustomerFacade();

    private final IManagerFacade managerFacade = new ManagerFacade();



    private final List<Token> customerTokens = new ArrayList<>();

    private List<Payment> report;

    private List<PaymentRequest> paymentRequests = new ArrayList<>();
    private UserAccount customer;
    private UserAccount merchant;
    private ClientErrorException exception;
    private PaymentRequest paymentRequest;

    private UserAccount retrievedCustomer;


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

    @And("the customer has an invalid token")
    public void theCustomerHasAnInvalidToken() {
        Token token = new Token();
        token.setId(
            UUID.randomUUID()
        );
        this.customerTokens.add(token);
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

    @And("the merchant has a bank account, that does not exist")
    public void theMerchantHasABankAccountThatDoesNotExist() {
        BankAccountNo bankAccountNo = new BankAccountNo();
        bankAccountNo.setBankAccountNo(
            UUID.randomUUID().toString()
        );

        this.merchant.setBankAccountNo(bankAccountNo);
    }

    @And("the customer has a bank account, that does not exist")
    public void theCustomerHasABankAccountThatDoesNotExist() {
        BankAccountNo bankAccountNo = new BankAccountNo();
        bankAccountNo.setBankAccountNo(
            UUID.randomUUID().toString()
        );

        this.customer.setBankAccountNo(bankAccountNo);
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

        try {
            this.merchantFacade.pay(
                this.paymentRequest
            );



        } catch (ClientErrorException e) {
            this.exception = e;
        }
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


    @When("the customer deregisters")
    public void theCustomerDeregisters() {
        this.customerFacade.deregister(
            this.customer.getId()
        );
    }

    @Then("the customer is no longer registered with DTU Pay")
    public void theCustomerIsNoLongerRegisteredWithDTUPay() {
        ClientErrorException e = Assertions.assertThrows(
            ClientErrorException.class,
            () -> this.customerFacade.getCustomer(
                this.customer.getId()
            )
        );

        Assert.assertEquals(
            404,
            e.getResponse().getStatus()
        );
    }

    @When("the customer requests a report")
    public void theCustomerRequestsAReport() {
       report = customerFacade.getReport(
            this.customer.getId()
        );

 
    }


    @Then("the customer should see a report with the following transaction details")
    public void theCustomerShouldSeeAReportWithTheFollowingTransactionDetails() {

        report.forEach(payment -> {
            Assert.assertEquals(
                this.customer.getBankAccountNo(),
                payment.getDebtor()
            );
        });

        
    }

    @When("the merchant requests a report")
    public void theMerchantRequestsAReport() {

        report = merchantFacade.getReport(
            this.merchant.getId()
        );

    }

    @Then("the merchant should see a report with the following transaction details")
    public void theMerchantShouldSeeAReportWithTheFollowingTransactionDetails() {
        report.forEach(payment -> {
            Assert.assertTrue(
                (this.merchant.getBankAccountNo() == payment.getCreditor()) && payment.getDebtor() == null
            );
        });

    }

    @And("the customer grants the payment with a token without discarding it")
    public void theCustomerGrantsThePaymentWithATokenWithoutDiscardingIt() {
        this.paymentRequest.setToken(
            this.customerTokens.getLast()
        );

        try {
            this.merchantFacade.pay(
                this.paymentRequest
            );

            this.paymentRequests.add(paymentRequest);
        } catch (ClientErrorException e) {
            this.exception = e;
        }
    }

    @When("the customer is retrieved by id")
    public void theCustomerIsRetrievedById() {
        this.retrievedCustomer = this.customerFacade.getCustomer(
            this.customer.getId()
        );
    }

    @When("the manager requests a report")
    public void theManagerRequestsAReport() {

        report = managerFacade.getReport();

    }

    @Then("the manager should see a report with the following transaction details")
    public void theManagerShouldSeeAReportWithTheFollowingTransactionDetails() {
       boolean result =  paymentRequests.stream()
                .allMatch(paymentRequest -> report.stream()
                        .anyMatch(payment -> Objects.equals(paymentRequest.getAmount(), payment.getAmount())));

         Assert.assertTrue(result);


    }

    @Then("expect the same customer")
    public void expectTheSameCustomer() {
        Assert.assertEquals(
            this.customer,
            this.retrievedCustomer
        );
    }

    @After
    public void cleanUp() {
        if (this.customer != null) {
            try {
                this.bankAdapter.retireBankAccount(
                        this.customer.getBankAccountNo()
                );
            } catch (Exception ignored) {
            }

            try {
                this.customerFacade.deregister(
                        this.customer.getId()
                );
            } catch (Exception ignored) {
            }
        }

        if (this.merchant != null) {
            try {
                this.bankAdapter.retireBankAccount(
                        this.merchant.getBankAccountNo()
                );
            } catch (Exception ignored) {
            }

            try {
                this.merchantFacade.deregister(
                        this.merchant.getId()
                );
            } catch (Exception ignored) {
            }
        }
    }


}

