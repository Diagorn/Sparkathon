package com.diagorn.sparkathon.event.model.hackathon;

import com.diagorn.sparkathon.event.model.base.AuditableEntityWithUser;
import com.diagorn.sparkathon.event.model.technology.Technology;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Hackathon itself
 */
@Entity
@Table(name = "hackathon")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(
        of = {
                "title",
                "description",
                "status",
                "registrationStart",
                "registrationEnd",
                "eventStart",
                "eventEnd",
                "maxTeamSize",
                "maxTeams"
        },
        callSuper = true
)
@ToString(
        of = {
                "title",
                "description",
                "status",
                "registrationStart",
                "registrationEnd",
                "eventStart",
                "eventEnd",
                "maxTeamSize",
                "maxTeams"
        },
        callSuper = true
)
public class Hackathon extends AuditableEntityWithUser {
    /**
     * Hackathon title
     */
    @Column(
            name = "title",
            nullable = false,
            length = 255
    )
    private String title;
    /**
     * Hackathon description
     */
    @Column(name = "description")
    private String description;
    /**
     * Hackathon current status
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "status_id")
    private HackathonStatus status;
    /**
     * Date-time of registration start
     */
    @Column(
            name = "registration_start",
            nullable = false
    )
    private LocalDateTime registrationStart;
    /**
     * Date-time of registration end
     */
    @Column(
            name = "registration_end",
            nullable = false
    )
    private LocalDateTime registrationEnd;
    /**
     * Date-time of event start
     */
    @Column(
            name = "event_start",
            nullable = false
    )
    private LocalDateTime eventStart;
    /**
     * Date-time of event end
     */
    @Column(
            name = "event_end",
            nullable = false
    )
    private LocalDateTime eventEnd;
    /**
     * Max team size
     */
    @Column(
            name = "max_team_size",
            nullable = false
    )
    private Integer maxTeamSize;
    /**
     * Maximum number of teams to be participating at the same time
     */
    @Column(name = "max_teams")
    private Integer maxTeams;
    /**
     * Technologies used by this hackathon
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "hackathon_technology_stack",
            joinColumns = @JoinColumn(name = "hackathon_id"),
            inverseJoinColumns = @JoinColumn(name = "technology_stack_id")
    )
    private List<Technology> technologies = new ArrayList<>();
    /**
     * Status history
     */
    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "hackathon",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private List<HackathonStatusHistory> history = new ArrayList<>();
    /**
     * Challenges
     */
    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "hackathon",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private Set<Challenge> challenges = new HashSet<>();
    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "hackathon",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private List<HackathonSchedule> events = new ArrayList<>();
}
