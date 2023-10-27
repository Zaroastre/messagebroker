package io.nirahtech.messagebroker;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

final class DefaultSubscriber implements Subscriber {

    private final Server server;
    private final Consumer<Event<Message<?>>> callback;
    private final Set<Event<Message<?>>> handledEvents = new HashSet<>();

    DefaultSubscriber(final Server server, Consumer<Event<Message<?>>> callback) {
        this.server = server;
        this.callback = callback;
    }

    @Override
    public void handle(Event<Message<?>> event) {
        if (!this.handledEvents.contains(event)) {
            this.callback.accept(event);
            this.handledEvents.add(event);
        }
    }
    
}
