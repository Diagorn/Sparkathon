package com.diagorn.sparkathon.auth.client;

import com.diagorn.sparkathon.auth.dto.kafka.NewUserContactsEvent;
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
    private final KafkaTemplate<Long, NewUserContactsEvent> newUserContactsEventKafkaTemplate;

    public void send(String json, String topic) {
        checkFields(json, topic);
        kafkaTemplate.send(topic, json);
    }

    @SneakyThrows
    public void send(Object data, String topic) {
        checkFields(data, topic);
        this.send(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data), topic);
    }

    public void sendUserContacts(NewUserContactsEvent data, String topic) {
        checkFields(data, topic);
        newUserContactsEventKafkaTemplate.send(topic, data.getId(), data);
    }

    private <T> void checkFields(T data, String topic) {
        Objects.requireNonNull(data, "Could not send message to kafka: data is null");
        Objects.requireNonNull(topic, "Could not send message to kafka: topic is null");
    }
}
