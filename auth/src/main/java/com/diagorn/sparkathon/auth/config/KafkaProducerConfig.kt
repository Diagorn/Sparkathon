package com.diagorn.sparkathon.auth.config;

import com.diagorn.sparkathon.auth.dto.kafka.NewUserContactsEvent;
import com.diagorn.sparkathon.auth.serializer.NewUserContactsEventSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration of Kafka producers
 *
 * @author diagorn
 */
@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        return newProducerFactory(StringSerializer.class, StringSerializer.class);
    }

    @Bean
    public ProducerFactory<Long, NewUserContactsEvent> newUserContactsEventProducerFactory() {
        return newProducerFactory(LongSerializer.class, NewUserContactsEventSerializer.class);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public KafkaTemplate<Long, NewUserContactsEvent> newUserContactsEventKafkaTemplate() {
        return new KafkaTemplate<>(newUserContactsEventProducerFactory());
    }

    @Bean
    public ObjectMapper objectMapper() {
        var mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    private <T, K> ProducerFactory<T, K> newProducerFactory(Class<?> keySerializerClass, Class<?> valueSerializerClass) {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapAddress
        );
        configProps.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                keySerializerClass
        );
        configProps.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                valueSerializerClass
        );
        return new DefaultKafkaProducerFactory<>(configProps);
    }
}
