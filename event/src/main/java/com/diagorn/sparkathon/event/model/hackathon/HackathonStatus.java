package com.diagorn.sparkathon.event.model.hackathon;

import com.diagorn.sparkathon.event.model.base.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Hackathon status
 *
 * @author diagorn
 */
@Entity
@Table(name = "hackathon_status")
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
@ToString(of =
        {
                "code",
                "name",
                "description"
        },
        callSuper = true
)
public class HackathonStatus extends AuditableEntity {
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
     * Hackathons with this status
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "status")
    private List<Hackathon> hackathons = new ArrayList<>();
}
