package dk.dtu.grp08.payment.domain.services;


import dk.dtu.grp08.payment.domain.adapters.IBankAdapter;
import dk.dtu.grp08.payment.domain.models.payment.BankAccountNo;
import dk.dtu.grp08.payment.domain.models.payment.Payment;
import dk.dtu.grp08.payment.domain.repositories.PaymentRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.SneakyThrows;

@ApplicationScoped
public class PaymentService implements IPaymentService {


    private IBankAdapter bankAdapter;

    @Inject
    private PaymentRepository paymentRepository;


    public void assignMerchant(Payment payment, BankAccountNo bankAccountNo) {
        payment.setCreditor(bankAccountNo);
    }

    public void assignCustomer(Payment payment, BankAccountNo bankAccountNo) {
        payment.setDebtor(bankAccountNo);
    }


    public void transferMoney(Payment payment) {
        bankAdapter.makeBankTransfer(payment);
        paymentRepository.addPayment(payment);
    }


}
