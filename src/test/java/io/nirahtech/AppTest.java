package io.nirahtech;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.Test;

import io.nirahtech.messagebroker.MessageBroker;
import io.nirahtech.messagebroker.MessageBrokerClient;
import io.nirahtech.messagebroker.QueueType;
import io.nirahtech.messagebroker.configuration.Configuration;

/**
 * Unit test for simple App.
 */
class AppTest {
    /**
     * Rigorous Test :-)
     * @throws InterruptedException
     */
    @Test
    void shouldAnswerWithTrue() throws IOException, InterruptedException {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final Configuration configuration = Configuration.builder().maxQueueSize(100).messageTTL(Duration.ofMinutes(1))
                .build();
        final String topic1 = "default";
        final MessageBroker messageBroker = new MessageBrokerClient(configuration);
        final QueueType queueType = QueueType.CHANNEL;
        final String payload = "Hello World!";
        messageBroker.producer().publish(queueType, topic1, payload);
        messageBroker.subscriber().subscribe(queueType, topic1, event -> {
            assertNotNull(event.data());
            assertNotNull(event.data().payload());
            assertTrue(payload.equals(event.data().payload()));
            atomicBoolean.set(true);
        });
        Thread.sleep(100);
        messageBroker.close();


        assertTrue(atomicBoolean.get());
    }

}
