package io.nirahtech.messagebroker.configuration;

import java.time.Duration;

public final class Configuration {
    private final int maxQueueSize;
    private final Duration messageTTL;

    public Configuration(int maxQueueSize, Duration messageTTL) {
        this.maxQueueSize = maxQueueSize;
        this.messageTTL = messageTTL;
    }

    public int getMaxQueueSize() {
        return maxQueueSize;
    }

    public Duration getMessageTTL() {
        return messageTTL;
    }

    public static ConfigurationBuilder builder() {
        return new ConfigurationBuilder();
    }
}
