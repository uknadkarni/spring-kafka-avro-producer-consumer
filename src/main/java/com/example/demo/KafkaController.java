package com.example.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.confluent.developer.User;

@Component
@RestController
@RequestMapping("/user")
public class KafkaController {
    private final Producer producer;

    private final Logger log = LogManager.getLogger(KafkaController.class);

    @Autowired
    public KafkaController(Producer producer){
        this.producer = producer;
    }

    @PostMapping("/publish")
    public ResponseEntity<String> publish(@RequestParam String name,
    @RequestParam Integer age){
        if(age < 0){
            return ResponseEntity.badRequest().body("Age must be a non-negative value");
        }

        User user = new User(name, age);
        log.warn("Publishing: " + user);
        producer.send(user);
        return ResponseEntity.ok("User " + user.toString() + " published successfully");
    }
    
}
