package com.example.demo1;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringKafkaAvroProducerConsumer {

    @Value("${topic.name}")
    private String topic;

    @Value("${topic.partitions-num}")
    private Integer partitions;

    @Value("${topic.replication-factor}")
    private Short replicationFactor;

    @Bean
    NewTopic newTopic() {
        return new NewTopic(topic, partitions, replicationFactor);
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringKafkaAvroProducerConsumer.class, args);
    }

}
