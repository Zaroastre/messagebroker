package com.adelya.broker;

import com.adelya.broker.configuration.Configuration;

final class DefaultProducer implements Producer {

    private final Server server;
    private final Configuration configuration;

    DefaultProducer(final Server server, Configuration configuration) {
        this.configuration = configuration;
        this.server = server;
    }

    @Override
    public <T> void publish(final QueueType queueType, String topic, T message) {
        this.server.publishMessage(queueType, topic, new Message<>(message));
    }
    
}
