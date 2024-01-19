package dk.dtu.grp08.customer.data.providers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import messaging.MessageQueue;
import messaging.implementations.RabbitMqQueue;

import java.util.Optional;

public class MessageQueueProvider {

    /**
     * @author Dilara Eda Celepli (s184262)
     */
    @Produces
    @ApplicationScoped
    public MessageQueue getMessageQueue() {
        return new RabbitMqQueue(
                Optional
                        .ofNullable(System.getenv("MQ_HOSTNAME"))
                        .orElse("localhost")
        );    }

}
