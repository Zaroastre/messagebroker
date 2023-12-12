package io.nirahtech.messagebroker;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import io.nirahtech.messagebroker.configuration.Configuration;

final class Channel implements MessageQueue {
    private final Configuration configuration;
    private final String name;
    private final List<AdvancedMessage> messages = new ArrayList<>();
    private Subscriber subscriber = null;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    Channel(final Configuration configuration, final String name) {
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
                    if (Objects.nonNull(this.subscriber)) {
                        this.subscriber.handle(new Event(now, this.name, message));
                    }
                });
            }
        }
    }

    @Override
    public void subscribe(final Subscriber subscriber) {
        this.subscriber = subscriber;
        executorService.submit(() -> {
            synchronized (this.messages) {
                this.messages.forEach(advancedMessage -> {
                    final LocalDateTime now = LocalDateTime.now();
                    subscriber.handle(new Event(now, this.name, advancedMessage.getMessage()));
                });
            }
        });
    }

    void unsubscribe(final Subscriber subscriber) {
        if (this.subscriber == subscriber) {
            this.subscriber = null;
        }
    }

    @Override
    public void close() throws IOException {
        this.executorService.shutdown();
        // this.executorService.close();
        this.scheduledExecutorService.shutdownNow();
        // this.scheduledExecutorService.close();
    }
}
