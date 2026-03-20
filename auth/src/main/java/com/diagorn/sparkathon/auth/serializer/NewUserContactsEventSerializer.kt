package com.diagorn.sparkathon.auth.serializer;

import com.diagorn.sparkathon.auth.dto.kafka.NewUserContactsEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * Serializer of NewUserContactsEvent class
 *
 * @author diagorn
 */
public class NewUserContactsEventSerializer implements Serializer<NewUserContactsEvent> {

    private final ObjectMapper objectMapper = this.initializeMapper();

    @Override
    @SneakyThrows
    public byte[] serialize(String topic, NewUserContactsEvent newUserContactsEvent) {
        NewUserContactsEvent toSerialize = Optional.ofNullable(newUserContactsEvent)
                .orElseThrow(() -> new IllegalArgumentException("Could not serialize newUserContactsEvent: object is null"));
        return objectMapper
                .writeValueAsString(toSerialize)
                .getBytes(StandardCharsets.UTF_8);
    }

    private ObjectMapper initializeMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }
}
