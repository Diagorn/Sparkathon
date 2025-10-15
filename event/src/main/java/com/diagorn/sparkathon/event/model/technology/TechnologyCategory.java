package com.diagorn.sparkathon.event.model.technology;

import com.diagorn.sparkathon.event.model.base.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Hackathon status
 *
 * @author diagorn
 */
@Entity
@Table(name = "technology_category")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(
        of = {
                "code",
                "name",
                "description"
        },
        callSuper = true
)
@ToString(
        of = {
                "code",
                "name",
                "description"
        },
        callSuper = true
)
public class TechnologyCategory extends AuditableEntity {
    /**
     * Status code
     */
    @Column(
            name = "code",
            nullable = false,
            unique = true,
            length = 50
    )
    private String code;
    /**
     * Status name
     */
    @Column(
            name = "name",
            nullable = false,
            length = 100
    )
    private String name;
    /**
     * Status description
     */
    @Column(name = "description")
    private String description;
    /**
     * Technologies with this category
     */
    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "category"
    )
    private Set<Technology> technologies = new HashSet<>();
}
