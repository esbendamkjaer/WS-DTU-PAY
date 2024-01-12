package dk.dtu.grp08.bdd;

import dk.dtu.grp08.bank.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.math.BigDecimal;
import java.util.List;

public class TokenSteps {

    private User customer;;

    private BankService bank;

    @Given("a customer {string} with bank account balance {int} kr")
    public void aCustomerWithNameAndBankAccountDetails(String firstName, int balance) {
        customer = new User();
        customer.setFirstName(firstName);

        bank = new BankServiceService().getBankServicePort();
        try {
            bank.createAccountWithBalance(customer,  new BigDecimal(balance));
        } catch (BankServiceException_Exception e) {
            throw new RuntimeException(e);
        }
    }

    @And("the customer is registered in DTU Pay")
    public void theCustomerIsRegisteredInDTUPay() {
        bank.registerInDTUPay(customer);
    }

    @And("the customer has {int} tokens")
    public void theCustomerHasTokens(int tokens) {
        //TODO
    }

    @When("the customer requests {int} tokens")
    public void theCustomerRequestsTokens(int tokens) {
        //TODO
    }

    @Then("the customer has {int} unused tokens")
    public void theCustomerHasUnusedTokens(int tokens) {
        //TODO
    }

    @When("the token has to be validated")
    public  void theTokenHasToBeValidated(){
        //TODO
    }


    @Then("the customer receives an exception")
    public void theCustomerReceivedAnException(){
        //TODO
    }

    @Then("the token is not validated")
    public void theTokenIsNotValidated(){
        //TODO
    }

    @Given("a token is not in the list")
    public void aTokenIsNotInTheList(){
        //TODO
    }

}

