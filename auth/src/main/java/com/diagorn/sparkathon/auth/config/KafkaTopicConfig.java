package com.diagorn.sparkathon.auth.config;

import com.diagorn.sparkathon.auth.config.properties.KafkaTopicProperties;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(KafkaTopicProperties.class)
public class KafkaTopicConfig {

    private final KafkaTopicProperties kafkaTopicProperties;

    @Bean
    public NewTopic newUserTopic() {
        return TopicBuilder.name(kafkaTopicProperties.getNewUser()).build();
    }

    @Bean
    public NewTopic editUserTopic() {
        return TopicBuilder.name(kafkaTopicProperties.getEditUser()).build();
    }
}
