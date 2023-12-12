package io.nirahtech.messagebroker;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import io.nirahtech.messagebroker.configuration.Configuration;

final class Topic implements MessageQueue {
    private final Configuration configuration;
    private final String name;
    private final List<AdvancedMessage> messages = new ArrayList<>();
    private final Set<Subscriber> subscribers = new HashSet<>();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    Topic(final Configuration configuration, final String name) {
        this.configuration = configuration;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void publish(Message<?> message) {
        if (this.messages.size() >= this.configuration.getMaxQueueSize()) {
            // Ignore
        } else {
            final LocalDateTime now = LocalDateTime.now();
            final AdvancedMessage advancedMessage = new AdvancedMessage(this.configuration, now, message);
            scheduledExecutorService.schedule(() -> {
                if (this.messages.contains(advancedMessage)) {
                    this.messages.remove(advancedMessage);
                }
            }, this.configuration.getMessageTTL().toMillis(), TimeUnit.MILLISECONDS);
            synchronized (this.messages) {
                this.messages.add(advancedMessage);
                executorService.submit(() -> {

                    this.subscribers.forEach(subscriber -> {
                        subscriber.handle(new Event(now, this.name, message));
                    });
                    if (this.messages.size() == this.configuration.getMaxQueueSize()) {
                        this.messages.remove(0);
                    }
                });
            }
        }
    }

    @Override
    public void subscribe(final Subscriber subscriber) {
        this.subscribers.add(subscriber);
        executorService.submit(() -> {
            synchronized (this.messages) {
                this.messages.forEach(advancedMessage -> {
                    final LocalDateTime now = LocalDateTime.now();
                    subscriber.handle(new Event(now, this.name, advancedMessage.getMessage()));
                });
            }
        });
    }

    @Override
    public void close() throws IOException {
        this.executorService.shutdown();
        // this.executorService.close();
        this.scheduledExecutorService.shutdownNow();
        // this.scheduledExecutorService.close();
    }
}
