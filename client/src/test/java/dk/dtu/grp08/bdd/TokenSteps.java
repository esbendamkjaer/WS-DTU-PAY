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

    @Given("a customer {string} with bank account balance {int} dkk")
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
        //TO DO
    }

    @When("the customer requests 5 tokens")
    public void theCustomerRequests5Tokens(int tokens){
        
    }


}

