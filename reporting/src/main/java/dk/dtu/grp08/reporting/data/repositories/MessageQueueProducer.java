package dk.dtu.grp08.reporting.data.repositories;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import messaging.MessageQueue;
import messaging.implementations.RabbitMqQueue;

public class MessageQueueProducer {

    @Produces
    @ApplicationScoped
    public MessageQueue createMessageQueue() {
        return new RabbitMqQueue("localhost");
    }


}
