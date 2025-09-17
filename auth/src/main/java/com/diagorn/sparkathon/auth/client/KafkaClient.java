package com.diagorn.sparkathon.auth.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Client that sends data to Kafka
 *
 * @author diagorn
 */
@Component
@RequiredArgsConstructor
public class KafkaClient {

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void send(String json, String topic) {
        Objects.requireNonNull(json, "Could not send message to kafka: data is null");
        Objects.requireNonNull(topic, "Could not send message to kafka: topic is null");

        kafkaTemplate.send(topic, json);
    }

    @SneakyThrows
    public void send(Object data, String topic) {
        Objects.requireNonNull(data, "Could not send message to kafka: data is null");
        Objects.requireNonNull(topic, "Could not send message to kafka: topic is null");

        this.send(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data), topic);
    }
}
