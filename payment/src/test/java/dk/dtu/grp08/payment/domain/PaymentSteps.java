package dk.dtu.grp08.payment.domain;

import dk.dtu.grp08.payment.data.repositories.PaymentRepository;
import dk.dtu.grp08.payment.domain.adapters.IBankAdapter;
import dk.dtu.grp08.payment.domain.events.*;
import dk.dtu.grp08.payment.domain.models.CorrelationId;
import dk.dtu.grp08.payment.domain.models.PaymentRequest;
import dk.dtu.grp08.payment.domain.models.Token;
import dk.dtu.grp08.payment.domain.models.payment.BankAccountNo;
import dk.dtu.grp08.payment.domain.models.payment.Payment;
import dk.dtu.grp08.payment.domain.models.user.UserAccountId;
import dk.dtu.grp08.payment.domain.services.PaymentService;
import dk.dtu.grp08.payment.domain.util.policy.PolicyManager;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import messaging.Event;
import messaging.MessageQueue;
import org.junit.jupiter.api.Assertions;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class PaymentSteps {

    private final MessageQueue messageQueue = mock(MessageQueue.class);
    private final IBankAdapter bankAdapter = mock(IBankAdapter.class);

    private PaymentRequest paymentRequest;
    private PaymentRequestedEvent paymentRequestedEvent;
    private PaymentInitiatedEvent paymentInitiatedEvent;
    private UserAccountId merchantId;
    private UserAccountId customerId;
    private CustomerBankAccountAssignedEvent customerBankAccountAssignedEvent;
    private MerchantBankAccountAssignedEvent merchantBankAccountAssignedEvent;

    private final ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);

    private PaymentService paymentService = new PaymentService(
        new PaymentRepository(),
        messageQueue,
        bankAdapter,
        new PolicyManager()
    );

    @When("a PaymentRequest has been received")
    public void aPaymentHasBeenRequested() {
        this.merchantId = UserAccountId.randomId();

        paymentRequest = new PaymentRequest(
            new Token(UUID.randomUUID()),
            this.merchantId.getId(),
            BigDecimal.valueOf(1000)
        );

        this.paymentRequestedEvent = new PaymentRequestedEvent(
            CorrelationId.randomId(),
            paymentRequest
        );

        this.paymentService.handlePaymentRequestedEvent(
            new Event(
                EventType.PAYMENT_REQUESTED.getEventName(),
                new Object[] {
                    this.paymentRequestedEvent
                }
            )
        );
    }

    @When("a CustomerBankAccountAssignedEvent is received")
    public void aCustomerBankAccountAssignedEventIsReceived() {
        this.customerId = UserAccountId.randomId();

        this.customerBankAccountAssignedEvent = new CustomerBankAccountAssignedEvent(
            this.paymentInitiatedEvent.getCorrelationId(),
            new BankAccountNo(
                UUID.randomUUID().toString()
            ),
            this.customerId
        );

        Event event = new Event(
            EventType.CUSTOMER_BANK_ACCOUNT_ASSIGNED.getEventName(),
            new Object[] {
                customerBankAccountAssignedEvent
            }
        );

        this.paymentService.handleCustomerBankAccountAssigned(
            event
        );
    }

    @When("a MerchantBankAccountAssignedEvent is received")
    public void aMerchantBankAccountAssignedEventIsReceived() {
        this.merchantBankAccountAssignedEvent = new MerchantBankAccountAssignedEvent(
            this.paymentInitiatedEvent.getCorrelationId(),
            new BankAccountNo(
                UUID.randomUUID().toString()
            ),
            this.merchantId
        );

        Event event = new Event(
            EventType.MERCHANT_BANK_ACCOUNT_ASSIGNED.getEventName(),
            new Object[] {
                merchantBankAccountAssignedEvent
            }
        );

        this.paymentService.handleMerchantBankAccountAssigned(
            event
        );
    }

    @When("a TokenInvalidatedEvent is received")
    public void aTokenInvalidatedEventIsReceived() {
        Event event = new Event(
            EventType.TOKEN_INVALIDATED.getEventName(),
            new Object[] {
                new TokenInvalidatedEvent(
                    this.paymentInitiatedEvent.getCorrelationId(),
                    this.paymentInitiatedEvent.getToken()
                )
            }
        );

        this.paymentService.handleTokenInvalidatedEvent(
            event
        );
    }

    @Then("the bank is asked to transfer the money")
    public void theBankIsAskedToTransferTheMoney() {
        verify(bankAdapter).makeBankTransfer(
            new Payment(
                null,
                this.customerBankAccountAssignedEvent.getBankAccountNo(),
                this.merchantBankAccountAssignedEvent.getBankAccountNo(),
                this.paymentInitiatedEvent.getAmount()
            )
        );
    }

    @Then("a corresponding PaymentTransferredEvent is sent")
    public void aCorrespondingPaymentTransferredEventIsSent() {
        verify(messageQueue, times(2)).publish(eventCaptor.capture());

        Event event = eventCaptor.getValue();
        PaymentTransferredEvent paymentTransferredEvent = event.getArgument(0, PaymentTransferredEvent.class);

        Event expected = new Event(
            EventType.PAYMENT_TRANSFERRED.getEventName(),
            new Object[] {
                new PaymentTransferredEvent(
                    this.paymentInitiatedEvent.getCorrelationId(),
                    new Payment(
                        paymentTransferredEvent.getPayment().getPaymentId(),
                        this.customerBankAccountAssignedEvent.getBankAccountNo(),
                        this.merchantBankAccountAssignedEvent.getBankAccountNo(),
                        this.paymentInitiatedEvent.getAmount()
                    ),
                    this.merchantId,
                    this.customerId
                )
            }
        );

        Assertions.assertEquals(
            expected,
            event
        );

        Assertions.assertNotNull(
            paymentTransferredEvent
                    .getPayment()
                    .getPaymentId()
        );
    }

    @Then("a PaymentInitiatedEvent is sent")
    public void aPaymentInitiatedEventIsSent() {
        verify(messageQueue).publish(eventCaptor.capture());

        Event event = eventCaptor.getValue();
        PaymentInitiatedEvent paymentInitiatedEvent = event.getArgument(0, PaymentInitiatedEvent.class);

        this.paymentInitiatedEvent = paymentInitiatedEvent;

        Assertions.assertEquals(
            paymentRequest.getMerchantId(),
            paymentInitiatedEvent.getMerchantID()
        );

        Assertions.assertEquals(
            paymentRequest.getToken(),
            paymentInitiatedEvent.getToken()
        );

        Assertions.assertEquals(
            paymentRequest.getAmount(),
            paymentInitiatedEvent.getAmount()
        );

        Assertions.assertNotNull(
            paymentInitiatedEvent
                .getCorrelationId()
                .getId()
        );
    }

    @Then("a corresponding PaymentFailedEvent is sent with cause {string}")
    public void aPaymentFailedEventIsSent(String cause) {
        Event event = new Event(
            EventType.PAYMENT_FAILED.getEventName(),
            new Object[] {
                new PaymentFailedEvent(
                    this.paymentInitiatedEvent.getCorrelationId(),
                    cause
                )
            }
        );

        verify(messageQueue).publish(event);
    }




}
