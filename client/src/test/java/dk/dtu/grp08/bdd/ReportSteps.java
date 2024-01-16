package dk.dtu.grp08.bdd;

import dk.dtu.grp08.bank.BankService;
import dk.dtu.grp08.bank.BankServiceException_Exception;
import dk.dtu.grp08.bank.BankServiceService;
import dk.dtu.grp08.bank.User;
import dk.dtu.grp08.dtupay.customer.CustomerFacade;
import dk.dtu.grp08.dtupay.customer.ICustomerFacade;
import dk.dtu.grp08.dtupay.models.Token;
import dk.dtu.grp08.dtupay.models.UserAccount;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.ws.rs.ClientErrorException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;



public class ReportSteps {

    private final List<Token> customerTokens = new ArrayList<>();

    private ClientErrorException exception;

    private final ICustomerFacade customerFacade = new CustomerFacade();
    UserAccount customer = new UserAccount();
    UserAccount merchant = new UserAccount();

    @Given("a customer with name {string} with CPR number {string} and bank account balance {int} kr")
    public void aCustomerWithNameAndBankAccountDetails(String firstName, String cpr, double balance) {
      
        customer.setName(firstName);
        customer.setCpr(cpr);

        BankService bank = new BankServiceService().getBankServicePort();
        try {
            bank.createAccountWithBalance(customer,  new BigDecimal(balance));
        } catch (BankServiceException_Exception e) {
            throw new RuntimeException(e);
        }
    }

    @And("a merchant with name {string} with CPR number {string} and account balance {int} kr")
    public void aMerchantWithNameWithCPRNumberAndAccountBalanceKr(String firstName, String cpr, int balance) {

        merchant.setName(firstName);
        merchant.setCpr(cpr);

        BankService bank = new BankServiceService().getBankServicePort();
        try {
            bank.createAccountWithBalance(merchant,  new BigDecimal(balance));
        } catch (BankServiceException_Exception e) {
            throw new RuntimeException(e);
        }
    }


    @And("a customer requests {int} tokens")
    public void aCustomerRequestsTokens(int count) {
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
