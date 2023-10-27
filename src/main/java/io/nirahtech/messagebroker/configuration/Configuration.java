package io.nirahtech.messagebroker.configuration;

import java.time.Duration;

public final record Configuration (
    int maxQueueSize,
    Duration messageTTL

) {

    public static final ConfigurationBuilder builder() {
        return new ConfigurationBuilder();
    }
}
