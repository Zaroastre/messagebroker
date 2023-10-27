package com.adelya.broker;

interface Subscriber  {
    void handle(Event<Message<?>> event);
}
