package com.diagorn.sparkathon.auth.domain

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

/**
 * Abstract entity with fields every entity has
 *
 * @author mikhail.gasin
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class AbstractEntity {
    /**
     * Date of creation
     */
    @Column(name = "created_at", updatable = false, nullable = false)
    @CreatedDate
    var createdAt: LocalDateTime = LocalDateTime.now()

    /**
     * Date of last modification
     */
    @Column(name = "updated_at")
    @LastModifiedDate
    var updatedAt: LocalDateTime = LocalDateTime.now()

    fun update() {
        updatedAt = LocalDateTime.now()
    }
}