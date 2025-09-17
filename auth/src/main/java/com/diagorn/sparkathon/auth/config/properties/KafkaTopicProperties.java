package com.diagorn.sparkathon.auth.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.kafka.topics")
@Data
public class KafkaTopicProperties {
    private String newUser;
    private String editUser;
}
