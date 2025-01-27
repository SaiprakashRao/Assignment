package com.Netcracker.Assignment.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "library-topic", groupId = "library-group")
    public void listen(String message) {
        System.out.println("Received message: " + message);
    }
}
