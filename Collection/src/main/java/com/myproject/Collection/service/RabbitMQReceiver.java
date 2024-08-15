package com.myproject.Collection.service;

import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class RabbitMQReceiver {
    //CountDownLatch allows one or more threads to wait until a threads completes.
    //1 means one operation to complete before the waiting thread procee.
    private CountDownLatch latch = new CountDownLatch(1);

    //receive Message
    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}
