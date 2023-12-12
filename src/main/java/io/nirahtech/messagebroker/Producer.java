package io.nirahtech.messagebroker;

public interface Producer {
    <T> void publish(QueueType queueType, String topic, T message);
}
