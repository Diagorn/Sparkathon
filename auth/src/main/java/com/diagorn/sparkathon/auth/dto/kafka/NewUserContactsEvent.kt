package com.diagorn.sparkathon.auth.dto.kafka

import java.time.LocalDateTime

class NewUserContactsEvent(
    /**
     * User identifier
     */
    val id: Long,
    /**
     * User telegram nickname
     */
    val telegramNickname: String,
    /**
     * User email
     */
    val email: String,
    /**
     * Time when user was created
     */
    val createdAt: LocalDateTime
)