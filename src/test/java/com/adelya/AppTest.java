package com.adelya;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.time.Duration;

import org.junit.Test;

import com.adelya.broker.MessageBroker;
import com.adelya.broker.MessageBrokerClient;
import com.adelya.broker.QueueType;
import com.adelya.broker.configuration.Configuration;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    public static void main(String[] args) throws IOException {
        final Configuration configuration = Configuration.builder().maxQueueSize(100).messageTTL(Duration.ofMinutes(1))
                .build();
        final String topic1 = "default";
        final MessageBroker messageBroker = new MessageBrokerClient(configuration);
        final QueueType queueType = QueueType.CHANNEL;
        messageBroker.producer().publish(queueType, topic1, "Hello World! (Cached message)");
        messageBroker.subscriber().subscribe(queueType, topic1, event -> {
            System.out.println(String.format("Message received: Event from %s on %s: %s", event.dateTime(),
                    event.subject(), event.data().payload()));
        });
        messageBroker.close();
    }
}
