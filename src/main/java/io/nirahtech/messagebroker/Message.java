package io.nirahtech.messagebroker;

public class Message<T> {
    private final T payload;

    public Message(T payload) {
        this.payload = payload;
    }

    public T getPayload() {
        return payload;
    }
}
