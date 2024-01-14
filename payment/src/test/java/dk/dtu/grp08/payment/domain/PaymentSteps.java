package dk.dtu.grp08.payment.domain;

import dk.dtu.grp08.payment.data.adapter.bank.stub.BankService;
import dk.dtu.grp08.payment.data.adapter.bank.stub.BankServiceService;
import dk.dtu.grp08.payment.domain.models.user.UserAccount;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;

public class PaymentSteps {

    BankService bank = new BankServiceService().getBankServicePort();
    private UserAccount customer = new UserAccount();



    @Given("a customer {String} {String} with cpr no {String}")
    public void customer(String firstName, String lastName, String cpr){
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setCpr(cpr);
    }

    @And("customer balance {int} kr is registered in a bank")
    public void customerBalance(int balance){


    }


}
