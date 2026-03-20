package com.diagorn.sparkathon.auth.dto.kafka;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Event of user creation
 *
 * @author diagorn
 */
@Data
@Builder
public class NewUserContactsEvent {
    /**
     * User identifier
     */
    private Long id;
    /**
     * User telegram nickname
     */
    private String telegramNickname;
    /**
     * User email
     */
    private String email;
    /**
     * Time when user was created
     */
    private LocalDateTime createdAt;
}
