package dk.dtu.grp08.payment.domain.services;

import dk.dtu.grp08.payment.domain.models.Token;
import dk.dtu.grp08.payment.domain.models.payment.BankAccountNo;
import dk.dtu.grp08.payment.domain.models.payment.Payment;
import lombok.SneakyThrows;

import java.math.BigDecimal;
import java.util.UUID;

public interface IPaymentService {

    void transferMoney(Payment payment, UUID merchantID, Token token);

    Payment requestPayment(
        final UUID merchantID,
        final Token token,
        final BigDecimal amount
    );

}
