package com.diagorn.sparkathon.auth.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Abstract entity with fields every entity has
 *
 * @author mikhail.gasin
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class AbstractEntity {
    /**
     * Date of creation
     */
    @Column(name = "created_at", updatable = false, nullable = false)
    @CreatedDate
    protected LocalDateTime createdAt;
    /**
     * Date of last modification
     */
    @Column(name = "updated_at")
    @LastModifiedDate
    protected LocalDateTime updatedAt;
}
