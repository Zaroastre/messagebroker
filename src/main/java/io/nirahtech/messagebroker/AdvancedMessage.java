package io.nirahtech.messagebroker;

import java.time.LocalDateTime;

import io.nirahtech.messagebroker.configuration.Configuration;

public final class AdvancedMessage {
    private final Configuration configuration;
    private final LocalDateTime createdDateTime;
    private final Message<?> message;

    public AdvancedMessage(Configuration configuration, LocalDateTime createdDateTime, Message<?> message) {
        this.configuration = configuration;
        this.createdDateTime = createdDateTime;
        this.message = message;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public Message<?> getMessage() {
        return message;
    }
}
