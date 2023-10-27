package io.nirahtech.messagebroker;

import java.io.Closeable;

public interface MessageBroker extends Closeable {
    Producer producer();
    Observer subscriber();
}
