package com.diagorn.sparkathon.auth.serializer

import com.diagorn.sparkathon.auth.dto.kafka.NewUserContactsEvent
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import lombok.SneakyThrows
import org.apache.kafka.common.serialization.Serializer
import java.nio.charset.StandardCharsets
import java.util.*


/**
 * Serializer of NewUserContactsEvent class
 *
 * @author diagorn
 */
class NewUserContactsEventSerializer : Serializer<NewUserContactsEvent> {
    private val objectMapper = this.initializeMapper()

    @SneakyThrows
    override fun serialize(topic: String, newUserContactsEvent: NewUserContactsEvent): ByteArray {
        val toSerialize = Optional.ofNullable(newUserContactsEvent)
            .orElseThrow {
                IllegalArgumentException(
                    "Could not serialize newUserContactsEvent: object is null"
                )
            }
        return objectMapper
            .writeValueAsString(toSerialize)
            .toByteArray(StandardCharsets.UTF_8)
    }

    private fun initializeMapper(): ObjectMapper {
        val mapper = ObjectMapper()
        mapper.registerModule(JavaTimeModule())
        return mapper
    }
}