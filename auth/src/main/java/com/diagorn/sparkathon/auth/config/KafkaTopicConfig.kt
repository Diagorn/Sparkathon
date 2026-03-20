package com.diagorn.sparkathon.auth.config

import com.diagorn.sparkathon.auth.config.properties.KafkaTopicProperties
import org.apache.kafka.clients.admin.NewTopic
import org.springframework.context.annotation.Bean
import org.springframework.kafka.config.TopicBuilder

/**
 * Configuration of kafka topics
 *
 * @author diagorn
 */
class KafkaTopicConfig(
    private val kafkaTopicProperties: KafkaTopicProperties
) {

    @Bean
    fun newUserTopic(): NewTopic = TopicBuilder
        .name(kafkaTopicProperties.newUser)
        .partitions(kafkaTopicProperties.newUserPartitionNum)
        .build()

    @Bean
    fun editUserTopic(): NewTopic = TopicBuilder
        .name(kafkaTopicProperties.editUser)
        .partitions(kafkaTopicProperties.editUserPartitionNum)
        .build()
}