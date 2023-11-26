package com.example.demo;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import io.confluent.developer.User;

@Service
public class Consumer {

    private final Logger log = LogManager.getLogger(Consumer.class);
    
    @Value("${topic.name}")
    private String topic;

    @KafkaListener(topics = "${topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(ConsumerRecord<String, User> record) {
        log.warn(String.format("Consumed message -> %s", record.value()));
    }
}
