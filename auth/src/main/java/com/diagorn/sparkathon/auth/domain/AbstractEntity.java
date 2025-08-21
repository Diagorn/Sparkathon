package com.diagorn.sparkathon.auth.domain;

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
public abstract class AbstractEntity {
    /**
     * Identifier
     */
    @Id
    @Column(name = "id")
    private Long id;
    /**
     * Date of creation
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    /**
     * Date of last modification
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
