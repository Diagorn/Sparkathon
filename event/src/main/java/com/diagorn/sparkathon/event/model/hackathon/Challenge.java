package com.diagorn.sparkathon.event.model.hackathon;

import com.diagorn.sparkathon.event.model.base.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "challenge")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(
        of = {
                "title",
                "description",
                "rules",
                "evaluationCriteria"
        },
        callSuper = true
)
@ToString(
        of = {
                "title",
                "description",
                "rules",
                "evaluationCriteria"
        },
        callSuper = true
)
public class Challenge extends AuditableEntity {
    /**
     * Hackathon
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hackathon_id")
    private Hackathon hackathon;
    /**
     * Hackathon title
     */
    @Column(
            name = "title",
            nullable = false
    )
    private String title;
    /**
     * Challenge description
     */
    @Column(
            name = "description",
            nullable = false
    )
    private String description;
    /**
     * Challenge rules
     */
    @Column(name = "rules")
    private String rules;
    /**
     * Evaluation criteria
     */
    @Column(name = "evaluation_criteria")
    private String evaluationCriteria;
}
