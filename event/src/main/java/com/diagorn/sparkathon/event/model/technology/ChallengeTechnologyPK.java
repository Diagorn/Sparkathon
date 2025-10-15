package com.diagorn.sparkathon.event.model.technology;

import com.diagorn.sparkathon.event.model.hackathon.Challenge;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.io.Serializable;

/**
 * Private key of challenge and technology
 *
 * @author diagorn
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ChallengeTechnologyPK implements Serializable {
    /**
     * Challenge
     */
    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;
    /**
     * Technology
     */
    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(name = "technology_id")
    private Technology technology;
}
