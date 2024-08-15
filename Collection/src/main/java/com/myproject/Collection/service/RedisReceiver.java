package com.myproject.Collection.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RedisReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisReceiver.class);
    //AtomicInteger is a thread-safe integer can be safely incremented or modified by multiple threads
    private AtomicInteger counter = new AtomicInteger();
    private List<String> messages = new ArrayList<>();

    //this method use to receive message through a Redis listener
    public void receiveMessage(String message) {
        LOGGER.info("Received <" + message + ">");
        messages.add(message);
        counter.incrementAndGet();
    }

    public int getCount() {
        return counter.get();
    }

    // Method to retrieve all received messages
    public List<String> getMessages() {
        return new ArrayList<>(messages);
    }
}