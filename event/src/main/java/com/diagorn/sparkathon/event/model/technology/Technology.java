package com.diagorn.sparkathon.event.model.technology;

import com.diagorn.sparkathon.event.model.base.AuditableEntity;
import com.diagorn.sparkathon.event.model.hackathon.Hackathon;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Technology
 *
 * @author diagorn
 */
@Entity
@Table(name = "technology_stack")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(
        of = {
                "name",
                "description"
        },
        callSuper = true
)
@ToString(
        of = {
                "name",
                "description"
        },
        callSuper = true
)
public class Technology extends AuditableEntity {
    /**
     * Technology name
     */
    @Column(
            name = "name",
            nullable = false,
            length = 100
    )
    private String name;
    /**
     * Technology description
     */
    @Column(name = "description")
    private String description;
    /**
     * Technology category
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private TechnologyCategory category;
    /**
     * Hackathons that use this technology
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "hackathon_technology_stack",
            joinColumns = @JoinColumn(name = "technology_stack_id"),
            inverseJoinColumns = @JoinColumn(name = "hackathon_id")
    )
    private Set<Hackathon> hackathons = new HashSet<>();
}
