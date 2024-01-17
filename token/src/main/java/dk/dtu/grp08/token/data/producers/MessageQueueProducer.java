package dk.dtu.grp08.token.data.producers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import messaging.MessageQueue;
import messaging.implementations.RabbitMqQueue;

import java.util.Optional;

public class MessageQueueProducer {

    @Produces
    @ApplicationScoped
    public MessageQueue createMessageQueue() {
        return new RabbitMqQueue(
            Optional
                .ofNullable(System.getenv("MQ_HOSTNAME"))
                .orElse("localhost")
        );
    }

}
