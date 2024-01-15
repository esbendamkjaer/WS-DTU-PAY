package dk.dtu.grp08.bdd;

import dk.dtu.grp08.bank.BankService;
import dk.dtu.grp08.bank.BankServiceException_Exception;
import dk.dtu.grp08.bank.BankServiceService;
import dk.dtu.grp08.bank.User;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.math.BigDecimal;

public class ReportSteps {
    User customer = new User();
    User merchant = new User();

    @Given("a customer with name {string} with CPR number {String} and bank account balance {int} kr")
    public void aCustomerWithNameAndBankAccountDetails(String firstName, String cpr, int balance) {
      
        customer.setFirstName(firstName);
        customer.setCprNumber(cpr);

        BankService bank = new BankServiceService().getBankServicePort();
        try {
            bank.createAccountWithBalance(customer,  new BigDecimal(balance));
        } catch (BankServiceException_Exception e) {
            throw new RuntimeException(e);
        }
    }

    @And("a merchant with name {string} with CPR number {string} and account balance {int} kr")
    public void aMerchantWithNameWithCPRNumberAndAccountBalanceKr(String firstName, String cpr, int balance) {

        merchant.setFirstName(firstName);
        merchant.setCprNumber(cpr);

        BankService bank = new BankServiceService().getBankServicePort();
        try {
            bank.createAccountWithBalance(merchant,  new BigDecimal(balance));
        } catch (BankServiceException_Exception e) {
            throw new RuntimeException(e);
        }
    }


    @And("a customer requests {int} tokens")
    public void aCustomerRequestsTokens(int arg0) {
        //TODO
        
    }

    @And("a merchant requests a payment of {int} kr from the customer")
    public void aMerchantRequestsAPaymentOfKrFromTheCustomer(int arg0) {
        //TODO
    }

    @When("the customer requests a report")
    public void theCustomerRequestsAReport() {
        //TODO
    }

    @Then("the customer should see a report with the following transaction details")
    public void theCustomerShouldSeeAReportWithTheFollowingTransactionDetails() {
        //TODO
    }

    @When("the merchant requests a report")
    public void theMerchantRequestsAReport() {
        //TODO
    }

    @Then("the merchant should see a report with the following transaction details")
    public void theMerchantShouldSeeAReportWithTheFollowingTransactionDetails() {
        //TODO
    }
}
