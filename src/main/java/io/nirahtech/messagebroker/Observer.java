package io.nirahtech.messagebroker;

import java.util.function.Consumer;

import io.nirahtech.messagebroker.configuration.Configuration;

public final class Observer {

    private final Server server;

    private final Configuration configuration;

    Observer(Server server, Configuration configuration) {
        this.configuration = configuration;
        this.server = server;
    }
    
    public final void subscribe(final QueueType queueType, final String topic, final Consumer<Event<Message<?>>> eventConsumer) {
        this.server.subscribe(queueType, topic, eventConsumer);
    }

}
