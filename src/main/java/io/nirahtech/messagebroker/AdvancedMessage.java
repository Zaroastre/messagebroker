package io.nirahtech.messagebroker;

import java.time.LocalDateTime;

import io.nirahtech.messagebroker.configuration.Configuration;

final record AdvancedMessage (
    Configuration configuration,
    LocalDateTime createdDateTime,
    Message<?> message
) {
    
}
