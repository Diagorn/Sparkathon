package com.diagorn.sparkathon.auth.client

import com.diagorn.sparkathon.auth.dto.kafka.NewUserContactsEvent
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import java.util.*

@Component
class KafkaClient(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val newUserContactsEventKafkaTemplate: KafkaTemplate<Long, NewUserContactsEvent>
) {
    fun send(json: String, topic: String) {
        validateFields(json, topic)
        kafkaTemplate.send(topic, json)
    }

    fun sendUserContacts(data: NewUserContactsEvent, topic: String) {
        validateFields(data, topic)
        newUserContactsEventKafkaTemplate.send(topic, data.id, data)
    }

    private fun <T> validateFields(data: T, topic: String) {
        Objects.requireNonNull(data, "Could not send message to kafka: data is null")
        Objects.requireNonNull(topic, "Could not send message to kafka: topic is null")
    }
}