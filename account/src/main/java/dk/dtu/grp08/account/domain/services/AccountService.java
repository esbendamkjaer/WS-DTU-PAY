package dk.dtu.grp08.account.domain.services;

import dk.dtu.grp08.account.domain.events.*;
import dk.dtu.grp08.account.domain.exceptions.NoSuchUserAccountException;
import dk.dtu.grp08.account.domain.models.user.UserAccount;
import dk.dtu.grp08.account.domain.models.user.UserAccountId;
import dk.dtu.grp08.account.domain.repository.IAccountRepository;
import dk.dtu.grp08.account.domain.models.user.BankAccountNo;
import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.val;
import messaging.Event;
import messaging.MessageQueue;

import java.util.List;
import java.util.Optional;

@Startup
@ApplicationScoped
public class AccountService implements IAccountService {

    private final IAccountRepository accountRepository;
    private final MessageQueue messageQueue;

    public AccountService(
            IAccountRepository accountRepository,
            MessageQueue messageQueue
    ) {
        this.accountRepository = accountRepository;
        this.messageQueue = messageQueue;

        this.messageQueue.addHandler(
            EventType.TOKEN_VALIDATED.getEventName(),
            this::handleTokenValidatedEvent
        );

        this.messageQueue.addHandler(
            EventType.PAYMENT_REQUESTED.getEventName(),
            this::handlePaymentRequestedEvent
        );

        this.messageQueue.addHandler(
            EventType.ACCOUNT_REGISTRATION_REQUESTED.getEventName(),
            this::handleAccountRegistrationRequestedEvent
        );

        this.messageQueue.addHandler(
            EventType.ACCOUNT_DEREGISTRATION_REQUESTED.getEventName(),
            this::handleAccountDeregistrationRequestedEvent
        );
    }

    @Override
    public UserAccount registerAccount(
        String name,
        String cpr,
        BankAccountNo bankAccountNo
    ) {
        UserAccount userAccount = new UserAccount();
        userAccount.setId(
            UserAccountId.randomId()
        );
        userAccount.setName(name);
        userAccount.setCpr(cpr);
        userAccount.setBankAccountNo(bankAccountNo);

        return accountRepository.createUserAccount(
            userAccount
        );
    }

    @Override
    public Optional<UserAccount> getUserAccountById(UserAccountId id) {
        return accountRepository.findById(id);
    }

    @Override
    public void deleteUserAccount(UserAccountId userAccountId) throws NoSuchUserAccountException {
        val userAccount = accountRepository.findById(userAccountId).orElseThrow(
            () -> new NoSuchUserAccountException(
                "No user account with id " + userAccountId.getId()
            )
        );

        accountRepository.deleteUserAccount(
            userAccount.getId()
        );
    }

    @Override
    public List<UserAccount> getUserAccounts() {
        return this.accountRepository.findAll();
    }

    public void handlePaymentRequestedEvent(Event event) {
        PaymentRequestedEvent paymentRequestedEvent = event.getArgument(0, PaymentRequestedEvent.class);

        System.out.println("payment requested event received");

        UserAccount merchant = this.getUserAccountById(
            new UserAccountId(paymentRequestedEvent.getMerchantID())
        ).get();

        BankAccountNo creditorBankAccountNo = merchant.getBankAccountNo();

        Event bankAccountNoAssignedEvent = new Event(
                EventType.MERCHANT_BANK_ACCOUNT_ASSIGNED.getEventName(),
                new Object[]{
                        new BankAccountNoAssignedEvent(
                                creditorBankAccountNo,
                                paymentRequestedEvent.getCorrelationId()
                        )
                }
        );

        this.messageQueue.publish(
            bankAccountNoAssignedEvent
        );
    }

    public void handleTokenValidatedEvent(Event event) {
        TokenValidatedEvent tokenValidatedEvent = event.getArgument(0, TokenValidatedEvent.class);

        System.out.println("token validated event received");

        this.getUserAccountById(
                tokenValidatedEvent.getUserAccountId()
        ).ifPresentOrElse(
                userAccount -> {
                    Event bankAccountNoAssignedEvent = new Event(
                            EventType.CUSTOMER_BANK_ACCOUNT_ASSIGNED.getEventName(),
                            new Object[]{
                                    new BankAccountNoAssignedEvent(
                                            userAccount.getBankAccountNo(),
                                            tokenValidatedEvent.getCorrelationId()
                                    )
                            }
                    );

                    this.messageQueue.publish(
                            bankAccountNoAssignedEvent
                    );
                },
                () -> {
                    Event tokenInvalidatedEvent = new Event(
                            EventType.TOKEN_INVALIDATED.getEventName(),
                            new Object[]{
                                    new TokenInvalidatedEvent(
                                            tokenValidatedEvent.getToken(),
                                            tokenValidatedEvent.getCorrelationId()
                                    )
                            }
                    );

                    this.messageQueue.publish(
                            tokenInvalidatedEvent
                    );
                }
        );
    }

    public void handleAccountRegistrationRequestedEvent(Event event) {
        AccountRegistrationRequestedEvent accountRegistrationRequestedEvent = event.getArgument(0, AccountRegistrationRequestedEvent.class);

        UserAccount userAccount = accountRegistrationRequestedEvent.getUserAccount();

        userAccount = this.registerAccount(
            userAccount.getName(),
            userAccount.getCpr(),
            userAccount.getBankAccountNo()
        );

        Event accountRegisteredEvent = new Event(
            EventType.ACCOUNT_REGISTERED.getEventName(),
            new Object[]{
                new AccountRegisteredEvent(
                    accountRegistrationRequestedEvent.getCorrelationId(),
                    userAccount
                )
            }
        );

        this.messageQueue.publish(
            accountRegisteredEvent
        );
    }

    public void handleAccountDeregistrationRequestedEvent(Event event) {
        AccountDeregistrationRequestedEvent accountDeregistrationRequestedEvent = event.getArgument(0, AccountDeregistrationRequestedEvent.class);

        UserAccountId userAccountId = accountDeregistrationRequestedEvent.getUserId();

        this.deleteUserAccount(
            userAccountId
        );

        Event accountDeregisteredEvent = new Event(
            EventType.ACCOUNT_DEREGISTERED.getEventName(),
            new Object[]{
                new AccountDeregisteredEvent(
                    accountDeregistrationRequestedEvent.getCorrelationId()
                )
            }
        );

        this.messageQueue.publish(
            accountDeregisteredEvent
        );
    }

}
