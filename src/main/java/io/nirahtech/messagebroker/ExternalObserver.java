package io.nirahtech.messagebroker;

import java.util.function.Consumer;

public final class ExternalObserver {

    ExternalObserver() {
        
    }
    
    public final void subscribe(final QueueType queueType, final String topic, final Consumer<Event<Message<?>>> eventConsumer) {
        
    }

}
