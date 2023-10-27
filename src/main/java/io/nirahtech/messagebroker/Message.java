package io.nirahtech.messagebroker;

public final record Message<T> (
    T payload
) {
    
}
