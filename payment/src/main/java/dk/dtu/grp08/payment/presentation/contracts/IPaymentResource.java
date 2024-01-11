package dk.dtu.grp08.payment.presentation.contracts;

import dk.dtu.grp08.payment.domain.models.Token;
import messaging.Event;

import java.math.BigDecimal;
import java.util.UUID;

public interface IPaymentResource {

    void requestPayment(UUID merchantID, Token token, BigDecimal amount);

    void handleMerchantBankAccountAssigned(Event mqEvent);


    void handleCustomBankAccountAssigned(Event mqEvent);
}

