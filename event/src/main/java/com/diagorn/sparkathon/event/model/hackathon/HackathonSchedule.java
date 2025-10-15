package com.diagorn.sparkathon.event.model.hackathon;

import com.diagorn.sparkathon.event.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;

/**
 * Hackathon events
 *
 * @author diagorn
 */
@Entity
@Table(name = "hackathon_schedule")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(
        of = {
                "title",
                "description",
                "startTime",
                "endTime",
                "type",
                "createdAt"
        },
        callSuper = true
)
@ToString(
        of = {
                "title",
                "description",
                "startTime",
                "endTime",
                "type",
                "createdAt"
        },
        callSuper = true
)
public class HackathonSchedule extends BaseEntity {
    /**
     * Hackathon
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hackathon_id")
    private Hackathon hackathon;
    /**
     * Event title
     */
    @Column(
            name = "title",
            nullable = false
    )
    private String title;
    /**
     * Event description
     */
    @Column(name = "description")
    private String description;
    /**
     * Event start time
     */
    @Column(
            name = "start_time",
            nullable = false
    )
    private LocalDateTime startTime;
    /**
     * Event end time
     */
    @Column(
            name = "end_time",
            nullable = false
    )
    private LocalDateTime endTime;
    @Column(
            name = "type",
            nullable = false,
            length = 50
    )
    private String type;
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
