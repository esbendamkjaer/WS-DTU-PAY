package dk.dtu.grp08.payment.domain.services;

import dk.dtu.grp08.payment.domain.models.Token;
import dk.dtu.grp08.payment.domain.models.payment.BankAccountNo;
import dk.dtu.grp08.payment.domain.models.payment.Payment;
import lombok.SneakyThrows;

import java.math.BigDecimal;
import java.util.UUID;

public interface IPaymentService {


    void assignMerchant(Payment payment, BankAccountNo bankAccountNo);

    void assignCustomer(Payment payment, BankAccountNo bankAccountNo);

    void transferMoney(Payment payment);

}
