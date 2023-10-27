package io.nirahtech.messagebroker;

import java.io.IOException;

import io.nirahtech.messagebroker.configuration.Configuration;

public final class MessageBrokerClient implements MessageBroker {

    private final Server server;
    private final Configuration configuration;
    private final Producer producer;
    private final Observer subscriber;

    public MessageBrokerClient(final Configuration configuration) {
        this.configuration = configuration;
        this.server = new Server(this.configuration);
        this.producer = new DefaultProducer(this.server, this.configuration);
        this.subscriber = new Observer(this.server, this.configuration);
    }

    @Override
    public Producer producer() {
        return this.producer;
    }
    @Override
    public Observer subscriber() {
        return this.subscriber;
    }

    @Override
    public void close() throws IOException {
        this.server.close();
    }
    
}
