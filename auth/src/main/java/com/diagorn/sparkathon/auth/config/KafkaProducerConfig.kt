package com.diagorn.sparkathon.auth.config

import com.diagorn.sparkathon.auth.dto.kafka.NewUserContactsEvent
import com.diagorn.sparkathon.auth.serializer.NewUserContactsEventSerializer
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.LongSerializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory

/**
 * Configuration of kafka producers
 *
 * @author diagorn
 */
@Configuration
class KafkaProducerConfig(
    @Value("\${spring.kafka.bootstrap-servers}")
    private val bootstrapAddress: String? = null
) {

    @Bean
    fun producerFactory(): ProducerFactory<String, String> = newProducerFactory(
            StringSerializer::class.java,
            StringSerializer::class.java
        )

    @Bean
    fun newUserContactsEventProducerFactory(): ProducerFactory<Long, NewUserContactsEvent> = newProducerFactory(
            LongSerializer::class.java,
            NewUserContactsEventSerializer::class.java
        )

    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, String> = KafkaTemplate(producerFactory())

    @Bean
    fun newUserContactsEventKafkaTemplate(): KafkaTemplate<Long, NewUserContactsEvent> =
        KafkaTemplate(newUserContactsEventProducerFactory())

    @Bean
    fun objectMapper(): ObjectMapper {
        val mapper = ObjectMapper()
        mapper.registerModule(JavaTimeModule())
        return mapper
    }

    private fun <T, K> newProducerFactory(
        keySerializerClass: Class<*>,
        valueSerializerClass: Class<*>
    ): ProducerFactory<T, K> {
        val configProps: MutableMap<String, Any?> = HashMap()
        configProps[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        configProps[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = keySerializerClass
        configProps[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] =
            valueSerializerClass
        return DefaultKafkaProducerFactory(configProps)
    }
}