package com.adelya.broker.configuration;

import java.time.Duration;

public final record Configuration (
    int maxQueueSize,
    Duration messageTTL

) {

    public static final ConfigurationBuilder builder() {
        return new ConfigurationBuilder();
    }
}
