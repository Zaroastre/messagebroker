package com.adelya.broker;

import java.time.LocalDateTime;

public record Event<T> (
    LocalDateTime dateTime,
    String subject,
    Message<T> data
) {
    
}
