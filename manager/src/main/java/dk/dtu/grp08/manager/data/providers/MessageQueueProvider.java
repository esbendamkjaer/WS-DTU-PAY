package dk.dtu.grp08.manager.data.providers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import messaging.MessageQueue;
import messaging.implementations.RabbitMqQueue;

import java.util.Optional;

public class MessageQueueProvider {

    @Produces
    @ApplicationScoped
    public MessageQueue getMessageQueue() {
        return new RabbitMqQueue(
                Optional
                        .ofNullable(System.getenv("MQ_HOSTNAME"))
                        .orElse("localhost")
        );    }

}
