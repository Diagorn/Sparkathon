package com.diagorn.sparkathon.event.model.base;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;

/**
 * Base superclass for all entities
 *
 * @author diagorn
 */
@MappedSuperclass
@EqualsAndHashCode
public class BaseEntity {
    /**
     * Identifier
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
