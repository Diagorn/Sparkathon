package com.diagorn.sparkathon.event.model.base;

import jakarta.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Auditable entity with user information
 *
 * @author diagorn
 */
@MappedSuperclass
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class AuditableEntityWithUser extends AuditableEntity {
    /**
     * Author user id
     */
    private Long createdBy;
}
