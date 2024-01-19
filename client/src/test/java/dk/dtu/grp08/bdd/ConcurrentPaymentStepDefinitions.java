package dk.dtu.grp08.bdd;

import dk.dtu.grp08.adapter.BankAdapter;
import dk.dtu.grp08.adapter.IBankAdapter;
import dk.dtu.grp08.dtupay.customer.CustomerFacade;
import dk.dtu.grp08.dtupay.customer.ICustomerFacade;
import dk.dtu.grp08.dtupay.merchant.IMerchantFacade;
import dk.dtu.grp08.dtupay.merchant.MerchantFacade;
import dk.dtu.grp08.dtupay.models.*;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;

public class ConcurrentPaymentStepDefinitions {

    private ICustomerFacade customerFacade = new CustomerFacade();
    private IMerchantFacade merchantFacade = new MerchantFacade();
    private IBankAdapter bankAdapter = new BankAdapter();
    private final List<UserAccount> customers = new ArrayList<>();
    private final Map<UserId, List<Token>> tokens = new HashMap<>();
    private final List<PaymentRequest> paymentRequests = new ArrayList<>();
    private final List<Payment> payments = new ArrayList<>();

    private BigDecimal initialMerchantBalance;
    private BigDecimal initialCustomerBalance;


    private final ExecutorService executorService = Executors.newFixedThreadPool(8);

    private UserAccount merchant;

    /**
     * @author Alexander Matzen (s233475)
     */
    @Given("{int} registered customers with a balance of {double} kr")
    public void customers(int count, double balance) {
        this.initialCustomerBalance = BigDecimal.valueOf(balance);

        for (int i = 0; i < count; i++) {
            UserAccount userAccount = new UserAccount();
            userAccount.setName("Customer " + i);
            userAccount.setCpr(UUID.randomUUID().toString());

            userAccount.setBankAccountNo(
                bankAdapter.createBankAccount(
                    userAccount.getName(),
                    "Some Lastname",
                    userAccount.getCpr(),
                    this.initialCustomerBalance
                )
            );

            userAccount = this.customerFacade.register(
                userAccount.getName(),
                userAccount.getCpr(),
                userAccount.getBankAccountNo()
            );

            customers.add(
                userAccount
            );
        }
    }

    /**
     * @author Clair Norah Mutebi (s184187)
     */
    @Given("a registered merchant with a balance of {double}")
    public void aRegisteredMerchantWithABalanceOf(double balance) {
        this.initialMerchantBalance = BigDecimal.valueOf(balance);

        UserAccount merchant = new UserAccount();
        merchant.setName("Merchant");
        merchant.setCpr(UUID.randomUUID().toString());

        BankAccountNo bankAccountNo = bankAdapter.createBankAccount(
            merchant.getName(),
            "Some Lastname",
            merchant.getCpr(),
            this.initialMerchantBalance
        );

        merchant.setBankAccountNo(bankAccountNo);

        merchant = this.customerFacade.register(
            merchant.getName(),
            merchant.getCpr(),
            merchant.getBankAccountNo()
        );

        this.merchant = merchant;
    }

    /**
     * @author Muhamad Hussein Nadali (s233479)
     */
    @And("each customer has {int} unused tokens")
    public void eachCustomerHasUnusedTokens(int count) {
        for (UserAccount customer : this.customers) {
            List<Token> tokens = this.customerFacade.getTokens(
                customer.getId(),
                count
            );

            this.tokens.computeIfAbsent(
                customer.getId(),
                k -> new ArrayList<>()
            ).addAll(tokens);
        }
    }

    /**
     * @author Esben Damkjær Sørensen (s233474)
     */
    @When("the merchant initiates a payment between {double} kr and {double} kr for each customer")
    public void theMerchantInitiatesAPaymentForEachCustomer(double min, double max) {
        BigDecimal amount = BigDecimal.valueOf(
            Math.round(
                Math.random() * (max - min) + min
            )
        );

        List<Callable<Payment>> tasks = new ArrayList<>();

        for (UserAccount customer : this.customers) {
            Token token = this.tokens.get(customer.getId()).removeLast();

            PaymentRequest paymentRequest = new PaymentRequest(
                token,
                this.merchant.getId().getId(),
                amount
            );
            this.paymentRequests.add(
                paymentRequest
            );

            tasks.add(
                () -> this.merchantFacade.pay(
                        paymentRequest
                    )
            );
        }

        List<Future<Payment>> payments;

        try {
            payments = executorService.invokeAll(tasks);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        for (Future<Payment> paymentFuture : payments) {
            try {
                this.payments.add(
                    paymentFuture.get()
                );
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * @author Fuad Hassan Jama (s233468)
     */
    @Then("the payments should have been assigned the correct bank account numbers")
    public void thePaymentsShouldHaveCorrectBankAccountNumbers() {

        for (int i = 0; i < this.payments.size(); i++) {
            Payment payment = this.payments.get(i);

            Assertions.assertEquals(
                this.merchant.getBankAccountNo(),
                payment.getCreditor()
            );

            Assertions.assertEquals(
                this.customers.get(i).getBankAccountNo(),
                payment.getDebtor()
            );
        }
    }

    /**
     * @author Dilara Eda Celepli (s184262)
     */
    @Then("the customers should have deducted the correct amount of money")
    public void theCustomersShouldHaveDeductedTheCorrectAmountOfMoney() {
        for (int i = 0; i < this.paymentRequests.size(); i++) {
            PaymentRequest paymentRequest = this.paymentRequests.get(i);
            Payment payment = this.payments.get(i);

            BigDecimal expectedBalance = this.initialCustomerBalance.subtract(
                paymentRequest.getAmount()
            );

            BigDecimal actualBalance = this.bankAdapter.getBalance(
                payment.getDebtor()
            );

            Assertions.assertEquals(
                expectedBalance,
                actualBalance
            );
        }
    }

    /**
     * @author Clair Norah Mutebi (s184187)
     */
    @Then("the merchant should have received the correct amount of money")
    public void theMerchantShouldHaveReceivedTheCorrectAmountOfMoney() {
        BigDecimal expectedBalance = this.initialMerchantBalance.add(
            this.paymentRequests.stream()
                .map(PaymentRequest::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
        );

        BigDecimal actualBalance = this.bankAdapter.getBalance(
            this.merchant.getBankAccountNo()
        );

        Assertions.assertEquals(
            expectedBalance,
            actualBalance
        );
    }

    /**
     * @author Muhamad Hussein Nadali (s233479)
     */
    @After
    public void cleanUp() {
        if (merchant != null) {
            this.bankAdapter.retireBankAccount(
                this.merchant.getBankAccountNo()
            );

            this.customerFacade.deregister(
                this.merchant.getId()
            );
        }

        for (UserAccount customer : this.customers) {
            this.bankAdapter.retireBankAccount(
                    customer.getBankAccountNo()
            );

            this.customerFacade.deregister(
                customer.getId()
            );
        }
    }

}
