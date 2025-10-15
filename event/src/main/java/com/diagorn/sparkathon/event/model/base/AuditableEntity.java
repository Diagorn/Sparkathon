package com.diagorn.sparkathon.event.model.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

/**
 * Entity with audition fields
 *
 * @author diagorn
 */
@MappedSuperclass
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class AuditableEntity extends BaseEntity {
    /**
     * Creation timestamp
     */
    @Column(name = "created_at")
    @CreationTimestamp
    private Instant createdAt;
    /**
     * Update timestamp
     */
    @Column(name = "updated_at")
    @UpdateTimestamp
    private Instant updatedAt;
}
