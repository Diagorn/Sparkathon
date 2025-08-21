package com.diagorn.sparkathon.auth.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * User role
 *
 * @author diagorn
 */
@Entity
@Table(name = "role")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Role extends AbstractEntity {
    /**
     * Role name
     */
    @Column(name = "name")
    private String name;
    /**
     * Users with given role
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
    private List<User> users;
}
