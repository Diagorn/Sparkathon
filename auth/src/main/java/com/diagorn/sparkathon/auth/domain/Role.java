package com.diagorn.sparkathon.auth.domain;

import lombok.*;

import jakarta.persistence.*;
import java.util.List;

/**
 * User role
 *
 * @author diagorn
 */
@Entity
@Table(name = "role")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Role extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id_gen")
    @SequenceGenerator(name = "role_id_gen", sequenceName = "role_id_seq", allocationSize = 1)
    @Column(name = "id")
    protected Long id;
    /**
     * Role name
     */
    @Column(name = "name")
    private String name;

    /**
     * Is role allowed for creation via API
     */
    @Column(name = "is_allowed_for_creation", nullable = false)
    private Boolean isAllowedForCreation;

    /**
     * Users with given role
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
    private List<User> users;
}
