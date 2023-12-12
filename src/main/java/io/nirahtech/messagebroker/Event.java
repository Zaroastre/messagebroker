package io.nirahtech.messagebroker;

import java.time.LocalDateTime;

public class Event<T> {
    private final LocalDateTime dateTime;
    private final String subject;
    private final Message<T> data;

    public Event(LocalDateTime dateTime, String subject, Message<T> data) {
        this.dateTime = dateTime;
        this.subject = subject;
        this.data = data;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getSubject() {
        return subject;
    }

    public Message<T> getData() {
        return data;
    }
}
