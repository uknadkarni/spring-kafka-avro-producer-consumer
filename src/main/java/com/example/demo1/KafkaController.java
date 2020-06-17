package com.example.demo1;

import io.confluent.developer.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RestController
@RequestMapping("/user")
public class KafkaController {

    private final Producer producer;

    private final Logger log = LogManager.getLogger(KafkaController.class);

    @Autowired
    public KafkaController(Producer producer) {
        this.producer = producer;
    }

    @RequestMapping("/publish")
    public void publish(@RequestParam String name,
                        @RequestParam Integer age) {
        User user = new User(name, age);
        log.info("Publishing: " + user);
        producer.send(user);
    }
}
