package com.adelya.broker;

import java.io.Closeable;

public interface MessageBroker extends Closeable {
    Producer producer();
    Observer subscriber();
}
