package com.diagorn.sparkathon.auth.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Topic properties for kafka
 *
 * @author diagorn
 */
@ConfigurationProperties(prefix = "spring.kafka.topics")
@Data
public class KafkaTopicProperties {
    /**
     * New user created topic
     */
    private String newUser;
    /**
     * Number of partitions for new user topic
     */
    private Integer newUserPartitionNum;
    /**
     * User edited topic
     */
    private String editUser;
    /**
     * Number of partitions for edit user topic
     */
    private Integer editUserPartitionNum;
}
