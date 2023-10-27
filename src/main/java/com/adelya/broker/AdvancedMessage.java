package com.adelya.broker;

import java.time.LocalDateTime;

import com.adelya.broker.configuration.Configuration;

final record AdvancedMessage (
    Configuration configuration,
    LocalDateTime createdDateTime,
    Message<?> message
) {
    
}
