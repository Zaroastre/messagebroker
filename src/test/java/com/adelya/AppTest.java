package com.adelya;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicBoolean;

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
     * @throws InterruptedException
     */
    @Test
    public void shouldAnswerWithTrue() throws IOException, InterruptedException {
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
