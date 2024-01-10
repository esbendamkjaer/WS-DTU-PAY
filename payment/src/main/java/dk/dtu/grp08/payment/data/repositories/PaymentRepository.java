package dk.dtu.grp08.payment.data.repositories;

import dk.dtu.grp08.payment.domain.repositories.IPaymentRepository;
import messaging.Event;

import java.util.ArrayList;
import java.util.List;

public class PaymentRepository implements IPaymentRepository {
    private List<Event> payments = new ArrayList<>();



}
