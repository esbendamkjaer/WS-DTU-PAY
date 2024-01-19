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

    /**
     * @author Alexander Matzen (s233475)
     */
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
            EventType.PAYMENT_INITIATED.getEventName(),
            this::handlePaymentInitiatedEvent
        );

        this.messageQueue.addHandler(
            EventType.ACCOUNT_REGISTRATION_REQUESTED.getEventName(),
            this::handleAccountRegistrationRequestedEvent
        );

        this.messageQueue.addHandler(
            EventType.ACCOUNT_DEREGISTRATION_REQUESTED.getEventName(),
            this::handleAccountDeregistrationRequestedEvent
        );

        this.messageQueue.addHandler(
            EventType.ACCOUNT_REQUESTED.getEventName(),
            this::handleAccountRequestedEvent
        );
    }

    /**
     * @author Esben Damkjær Sørensen (s233474)
     */
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

    /**
     * @author Fuad Hassan Jama (s233468)
     */
    @Override
    public Optional<UserAccount> getUserAccountById(UserAccountId id) {
        return accountRepository.findById(id);
    }


    /**
     * @author Dilara Eda Celepli (s184262)
     */
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

    /**
     * @author Clair Norah Mutebi (s184187)
     */
    @Override
    public List<UserAccount> getUserAccounts() {
        return this.accountRepository.findAll();
    }

    /**
     * @author Muhamad Hussein Nadali (s233479)
     */
    public void handlePaymentInitiatedEvent(Event event) {
        PaymentInitiatedEvent paymentInitiatedEvent = event.getArgument(0, PaymentInitiatedEvent.class);

        UserAccount merchant = this.getUserAccountById(
            new UserAccountId(paymentInitiatedEvent.getMerchantID())
        ).get();

        BankAccountNo creditorBankAccountNo = merchant.getBankAccountNo();

        Event bankAccountNoAssignedEvent = new Event(
                EventType.MERCHANT_BANK_ACCOUNT_ASSIGNED.getEventName(),
                new Object[]{
                        new BankAccountNoAssignedEvent(
                                creditorBankAccountNo,
                                paymentInitiatedEvent.getCorrelationId(),
                                new UserAccountId(paymentInitiatedEvent.getMerchantID())
                        )
                }
        );

        this.messageQueue.publish(
            bankAccountNoAssignedEvent
        );
    }

    /**
     * @author Esben Damkjær Sørensen (s233474)
     */
    public void handleTokenValidatedEvent(Event event) {
        TokenValidatedEvent tokenValidatedEvent = event.getArgument(0, TokenValidatedEvent.class);

        this.getUserAccountById(
                tokenValidatedEvent.getUserAccountId()
        ).ifPresentOrElse(
                userAccount -> {
                    Event bankAccountNoAssignedEvent = new Event(
                            EventType.CUSTOMER_BANK_ACCOUNT_ASSIGNED.getEventName(),
                            new Object[]{
                                    new BankAccountNoAssignedEvent(
                                            userAccount.getBankAccountNo(),
                                            tokenValidatedEvent.getCorrelationId(),
                                            userAccount.getId()
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

    /**
     * @author Alexander Matzen (s233475)
     */
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

    /**
     * @author Fuad Hassan Jama (s233468)
     */
    public void handleAccountDeregistrationRequestedEvent(Event event) {
        AccountDeregistrationRequestedEvent accountDeregistrationRequestedEvent = event.getArgument(0, AccountDeregistrationRequestedEvent.class);

        UserAccountId userAccountId = accountDeregistrationRequestedEvent.getUserId();

        try {
            this.deleteUserAccount(
                userAccountId
            );
        } catch (NoSuchUserAccountException e) {
            Event userNotFoundEvent = new Event(
                EventType.USER_NOT_FOUND.getEventName(),
                new Object[]{
                    new UserNotFoundEvent(
                        accountDeregistrationRequestedEvent.getCorrelationId(),
                        userAccountId
                    )
                }
            );

            this.messageQueue.publish(
                userNotFoundEvent
            );

            return;
        }

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

    /**
     * @author Dilara Eda Celepli (s184262)
     */
    public void handleAccountRequestedEvent(Event event) {
        AccountRequestedEvent accountRequestedEvent = event.getArgument(0, AccountRequestedEvent.class);

        UserAccountId userAccountId = accountRequestedEvent.getUserId();

        Optional<UserAccount> userAccount = this.getUserAccountById(
            userAccountId
        );

        if (userAccount.isEmpty()) {
            Event userNotFoundEvent = new Event(
                EventType.USER_NOT_FOUND.getEventName(),
                new Object[]{
                    new UserNotFoundEvent(
                        accountRequestedEvent.getCorrelationId(),
                        userAccountId
                    )
                }
            );

            this.messageQueue.publish(
                userNotFoundEvent
            );

        } else {
            Event accountReturnedEvent = new Event(
                    EventType.ACCOUNT_RETURNED.getEventName(),
                    new Object[]{
                            new AccountReturnedEvent(
                                    accountRequestedEvent.getCorrelationId(),
                                    this.getUserAccountById(userAccountId).orElse(null)
                            )
                    }
            );

            this.messageQueue.publish(
                accountReturnedEvent
            );
        }

    }

}
