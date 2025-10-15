package com.diagorn.sparkathon.event.model.technology;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "challenge_technology")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {
        "challengeTechnology",
        "isRequired",
})
@ToString(of = {
        "challengeTechnology",
        "isRequired"
})
public class ChallengeTechnology {
    /**
     * Challenge and technology
     */
    @EmbeddedId
    private ChallengeTechnologyPK challengeTechnology;
    /**
     * Is this technology required for hackathon?
     */
    @Column(
            name = "is_required",
            nullable = false
    )
    private Boolean isRequired;
    /**
     * Creation timestamp
     */
    @Column(name = "created_at")
    @CreationTimestamp
    private Instant createdAt;
}
