package com.diagorn.sparkathon.auth.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * Topic properties for kafka
 *
 * @author diagorn
 */
@ConfigurationProperties(prefix = "spring.kafka.topics")
class KafkaTopicProperties(
    /**
     * New user created topic
     */
    val newUser: String,
    /**
     * Number of partitions for new user topic
     */
    val newUserPartitionNum: Int,

    /**
     * User edited topic
     */
    val editUser: String,

    /**
     * Number of partitions for edit user topic
     */
    val editUserPartitionNum: Int
)