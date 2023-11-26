package com.example.demo;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {
	@Value("${topic.name}")
	private String topic;

	@Value("${topic.partitions}")
	private Integer partitions;

	@Value("${topic.replication-factor}")
	private Short replicationFactor;

	@Bean
	NewTopic newTopic(){
		return new NewTopic(topic, partitions, replicationFactor);
	}
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
