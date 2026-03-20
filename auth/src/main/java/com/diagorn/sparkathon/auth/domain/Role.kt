package com.diagorn.sparkathon.auth.domain

import jakarta.persistence.*

/**
 * User role
 *
 * @author diagorn
 */
@Entity
@Table(name = "role")
class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id_gen")
    @SequenceGenerator(name = "role_id_gen", sequenceName = "role_id_seq", allocationSize = 1)
    @Column(name = "id")
    var id: Long = 0,

    /**
     * Role name
     */
    @Column(name = "name")
    var name: String,

    /**
     * Is role allowed for creation via API
     */
    @Column(name = "is_allowed_for_creation", nullable = false)
    var isAllowedForCreation: Boolean = false,

    /**
     * Users with given role
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
    val users: List<User> = emptyList()
) : AbstractEntity()