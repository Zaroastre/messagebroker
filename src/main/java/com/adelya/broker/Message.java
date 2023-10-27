package com.adelya.broker;

public final record Message<T> (
    T payload
) {
    
}
