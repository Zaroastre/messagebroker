package com.adelya.broker;

public sealed interface Producer permits DefaultProducer, ExternalProducer {
    <T> void publish(QueueType queueType, String topic, T message);
}
