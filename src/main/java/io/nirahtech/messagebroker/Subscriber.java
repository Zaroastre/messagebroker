package io.nirahtech.messagebroker;

interface Subscriber  {
    void handle(Event<Message<?>> event);
}
