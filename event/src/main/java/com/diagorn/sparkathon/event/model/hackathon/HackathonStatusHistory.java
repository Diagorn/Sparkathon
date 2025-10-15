package com.diagorn.sparkathon.event.model.hackathon;

import com.diagorn.sparkathon.event.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

/**
 * Hackathon status change
 */
@Entity
@Table(name = "hackathon_status_history")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(
        of = {
                "changedBy",
                "changeReason"
        },
        callSuper = true
)
@ToString(
        of = {
                "changedBy",
                "changeReason"
        },
        callSuper = true
)
public class HackathonStatusHistory extends BaseEntity {
    /**
     * Hackathon
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hackathon_id")
    private Hackathon hackathon;
    /**
     * Old status id (could be null)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "old_status_id")
    private HackathonStatus oldStatus;
    /**
     * New status id
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "new_status_id")
    private HackathonStatus newStatus;
    /**
     * User changed the status
     */
    @Column(
            name = "changed_by",
            nullable = false
    )
    private Long changedBy;
    /**
     * Status change reason.
     * Is filled when status changes outside the common flow
     */
    @Column(name = "change_reason")
    private String changeReason;
    /**
     * Date of creation
     */
    @Column(
            name = "created_at",
            nullable = false
    )
    @CreationTimestamp
    private Instant createdAt;
}
