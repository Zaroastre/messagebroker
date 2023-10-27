package com.adelya.broker;

import java.io.Closeable;

public interface MessageQueue extends Closeable {
    String getName();
    void publish(final Message<?> message);
    void subscribe(final Subscriber subscriber);
}
