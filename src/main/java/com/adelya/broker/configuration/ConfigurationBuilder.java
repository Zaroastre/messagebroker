package com.adelya.broker.configuration;

import java.time.Duration;

import com.adelya.broker.Builder;

public final class ConfigurationBuilder implements Builder<Configuration> {

    private int maxQueueSize = 1_000;
    private Duration messageTTL = Duration.ofHours(6);
  
    public ConfigurationBuilder maxQueueSize(int maxQueueSize) {
        this.maxQueueSize = maxQueueSize;
        return this;
    }
    public ConfigurationBuilder messageTTL(Duration messageTTL) {
        this.messageTTL = messageTTL;
        return this;
    }
    
    @Override
    public Configuration build() {
        return new Configuration(maxQueueSize, messageTTL);
    }
}