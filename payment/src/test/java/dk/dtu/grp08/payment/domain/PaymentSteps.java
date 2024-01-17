package dk.dtu.grp08.payment.domain;

import dk.dtu.grp08.payment.data.repositories.PaymentRepository;
import dk.dtu.grp08.payment.domain.adapters.IBankAdapter;
import dk.dtu.grp08.payment.domain.events.*;
import dk.dtu.grp08.payment.domain.models.PaymentRequest;
import dk.dtu.grp08.payment.domain.models.Token;
import dk.dtu.grp08.payment.domain.models.payment.BankAccountNo;
import dk.dtu.grp08.payment.domain.models.payment.Payment;
import dk.dtu.grp08.payment.domain.services.PaymentService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import messaging.Event;
import messaging.MessageQueue;
import org.junit.jupiter.api.Assertions;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PaymentSteps {

    private final MessageQueue messageQueue = mock(MessageQueue.class);
    private final IBankAdapter bankAdapter = mock(IBankAdapter.class);

    private PaymentRequest paymentRequest;
    private PaymentRequestedEvent paymentRequestedEvent;
    private CustomerBankAccountAssignedEvent customerBankAccountAssignedEvent;
    private MerchantBankAccountAssignedEvent merchantBankAccountAssignedEvent;

    private final ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);

    private PaymentService paymentService = new PaymentService(
        new PaymentRepository(),
        messageQueue,
        bankAdapter
    );

    @When("a payment has been requested")
    public void aPaymentHasBeenRequested() {
        paymentRequest = new PaymentRequest(
            new Token(UUID.randomUUID()),
            UUID.randomUUID(),
            BigDecimal.valueOf(1000)
        );

        this.paymentService.requestPayment(
            paymentRequest.getMerchantId(),
            paymentRequest.getToken(),
            paymentRequest.getAmount()
        );
    }

    @When("a CustomerBankAccountAssignedEvent is received")
    public void aCustomerBankAccountAssignedEventIsReceived() {
        this.customerBankAccountAssignedEvent = new CustomerBankAccountAssignedEvent(
            this.paymentRequestedEvent.getCorrelationId(),
            new BankAccountNo(
                UUID.randomUUID().toString()
            )
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
            this.paymentRequestedEvent.getCorrelationId(),
            new BankAccountNo(
                UUID.randomUUID().toString()
            )
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
                    this.paymentRequestedEvent.getCorrelationId(),
                    this.paymentRequestedEvent.getToken()
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
                this.customerBankAccountAssignedEvent.getBankAccountNo(),
                this.merchantBankAccountAssignedEvent.getBankAccountNo(),
                this.paymentRequestedEvent.getAmount()
            )
        );
    }

    @Then("a corresponding PaymentTransferredEvent is sent")
    public void aCorrespondingPaymentTransferedEventIsSent() {
        Event event = new Event(
            EventType.PAYMENT_TRANSFERRED.getEventName(),
            new Object[] {
                new PaymentTransferredEvent(
                    this.paymentRequestedEvent.getMerchantID(),
                    this.paymentRequestedEvent.getToken(),
                    this.paymentRequestedEvent.getAmount()
                )
            }
        );

        verify(messageQueue).publish(event);
    }

    @Then("a PaymentRequestedEvent is sent")
    public void aPaymentRequestedEventIsSent() {
        verify(messageQueue).publish(eventCaptor.capture());

        Event event = eventCaptor.getValue();
        PaymentRequestedEvent paymentRequestedEvent = event.getArgument(0, PaymentRequestedEvent.class);

        this.paymentRequestedEvent = paymentRequestedEvent;

        Assertions.assertEquals(
            paymentRequest.getMerchantId(),
            paymentRequestedEvent.getMerchantID()
        );

        Assertions.assertEquals(
            paymentRequest.getToken(),
            paymentRequestedEvent.getToken()
        );

        Assertions.assertEquals(
            paymentRequest.getAmount(),
            paymentRequestedEvent.getAmount()
        );

        Assertions.assertNotNull(
            paymentRequestedEvent
                .getCorrelationId()
                .getId()
        );
    }

    @Then("a corresponding PaymentCanceledEvent is sent")
    public void aPaymentCanceledEventIsSent() {
        Event event = new Event(
            EventType.PAYMENT_CANCELED.getEventName(),
            new Object[] {
                new PaymentCanceledEvent(
                    this.paymentRequestedEvent.getCorrelationId()
                )
            }
        );

        verify(messageQueue).publish(event);
    }




}
