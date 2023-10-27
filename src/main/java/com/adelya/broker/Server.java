package com.adelya.broker;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

import com.adelya.broker.configuration.Configuration;

final class Server implements Closeable {
    private final Set<MessageQueue> messageQueues = new HashSet<>();
    private final Configuration configuration;

    Server(Configuration configuration) {
        this.configuration = configuration;
    }

    final void publishMessage(final QueueType queueType, final String subject, final Message<?> message) {
        Optional<MessageQueue> existingQueue = this.messageQueues.stream()
                .filter(queue -> queue.getName().equalsIgnoreCase(subject)).findFirst();
        existingQueue.ifPresentOrElse((queue) -> {
            queue.publish(message);
        }, () -> {
            MessageQueue newTopic;
            switch (queueType) {
                case CHANNEL:
                    newTopic = new Channel(this.configuration, subject);
                    break;
                case TOPIC:
                    newTopic = new Topic(this.configuration, subject);
                    break;
                default:
                    throw new RuntimeException("Unsopported QueueType: " + queueType);
            }
            this.messageQueues.add(newTopic);
            newTopic.publish(message);
        });
    }

    final void subscribe(final QueueType queueType, final String subject, final Consumer<Event<Message<?>>> consumer) {
        Optional<MessageQueue> existingTopic = this.messageQueues.stream()
                .filter(queue -> queue.getName().equalsIgnoreCase(subject)).findFirst();
        existingTopic.ifPresentOrElse((queue) -> {
            queue.subscribe(new DefaultSubscriber(this, consumer));
        }, () -> {
            MessageQueue newTopic;
            switch (queueType) {
                case CHANNEL:
                    newTopic = new Channel(this.configuration, subject);
                    break;
                case TOPIC:
                    newTopic = new Topic(this.configuration, subject);
                    break;
                default:
                    throw new RuntimeException("Unsopported QueueType: " + queueType);
            }

            this.messageQueues.add(newTopic);
            newTopic.subscribe(new DefaultSubscriber(this, consumer));
        });
    }

    @Override
    public void close() throws IOException {
        this.messageQueues.forEach(queue -> {
            try {
                queue.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
