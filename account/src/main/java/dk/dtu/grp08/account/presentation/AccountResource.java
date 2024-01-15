package dk.dtu.grp08.account.presentation;

import dk.dtu.grp08.account.domain.events.*;
import dk.dtu.grp08.account.domain.models.user.BankAccountNo;
import dk.dtu.grp08.account.domain.models.user.UserAccountId;
import dk.dtu.grp08.account.presentation.contracts.IAccountResource;
import dk.dtu.grp08.account.domain.services.AccountService;
import dk.dtu.grp08.account.domain.models.user.UserAccount;
import messaging.Event;
import messaging.MessageQueue;
import messaging.implementations.RabbitMqQueue;

import java.util.Optional;
import java.util.UUID;

public class AccountResource implements IAccountResource {

    private final AccountService accountService;

    private final MessageQueue messageQueue = new RabbitMqQueue("localhost");

    public AccountResource(AccountService accountService) {
        this.accountService = accountService;

        this.messageQueue.addHandler(
            EventType.TOKEN_VALIDATED.getEventName(),
            this::handleTokenValidatedEvent
        );

        this.messageQueue.addHandler(
            EventType.PAYMENT_REQUESTED.getEventName(),
            this::handlePaymentRequestedEvent
        );
    }

    @Override
    public UserAccount createUserAccount(
        UserAccount userAccount
    ) {
        return this.accountService.registerAccount(
            userAccount.getName(),
            userAccount.getCpr(),
            userAccount.getBankAccountNo()
        );
    }

    @Override
    public Optional<UserAccount> getUserAccount(UUID id) {
        return accountService.getUserAccountById(
            new UserAccountId(id)
        );
    }

    @Override
    public UserAccount[] getAllUserAccounts() {
        return new UserAccount[0];
    }

    @Override
    public void deleteUserAccount(UUID id) {
        this.accountService.deleteUserAccount(
            new UserAccountId(id)
        );
    }

    public void handlePaymentRequestedEvent(Event event) {
        PaymentRequestedEvent paymentRequestedEvent = event.getArgument(0, PaymentRequestedEvent.class);

        System.out.println("payment requested event received");

        UserAccount merchant = this.accountService.getUserAccountById(
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
        this.accountService.getUserAccountById(
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

}
