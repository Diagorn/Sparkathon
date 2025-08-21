package com.diagorn.sparkathon.auth.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * Abstract entity with fields every entity has
 *
 * @author mikhail.gasin
 */
@MappedSuperclass
@Getter
@Setter
public abstract class AbstractEntity {
    /**
     * Identifier
     */
    @Id
    @Column(name = "id")
    protected Long id;
    /**
     * Date of creation
     */
    @Column(name = "created_at")
    protected LocalDateTime createdAt;
    /**
     * Date of last modification
     */
    @Column(name = "updated_at")
    protected LocalDateTime updatedAt;
}
