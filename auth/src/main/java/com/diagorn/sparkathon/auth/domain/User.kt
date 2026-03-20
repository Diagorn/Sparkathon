package com.diagorn.sparkathon.auth.domain

import com.diagorn.sparkathon.auth.dto.kafka.NewUserContactsEvent
import com.diagorn.sparkathon.common.exception.BadRequestException
import com.diagorn.sparkathon.common.exception.SparkathonException
import jakarta.persistence.*
import org.apache.commons.lang3.StringUtils
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "usr")
class User(
    /**
     * Identifier
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usr_id_gen")
    @SequenceGenerator(name = "usr_id_gen", sequenceName = "usr_id_seq", allocationSize = 1)
    @Column(name = "id")
    var id: Long = 0,

    /**
     * User role
     */
    @ManyToOne(targetEntity = Role::class, fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    var role: Role,

    /**
     * First name
     */
    @Column(name = "first_name", nullable = false)
    var firstName: String,

    /**
     * Middle name
     */
    @Column(name = "middle_name")
    var middleName: String?,

    /**
     * Last name
     */
    @Column(name = "last_name", nullable = false)
    var lastName: String,

    /**
     * User login (username)
     */
    @Column(name = "login", nullable = false, unique = true)
    var login: String,

    /**
     * User password
     */
    @Column(name = "password", nullable = false)
    var password: String,

    /**
     * User email
     */
    @Column(name = "email", nullable = false, unique = true)
    var email: String,

    /**
     * Telegram nickname
     */
    @Column(name = "telegram_nickname", nullable = false, unique = true)
    var telegramNickname: String
) : AbstractEntity(), UserDetails {

    fun fullName(): String {
        if (firstName.isEmpty() || lastName.isEmpty()) {
            throw BadRequestException("User with id $id has invalid firstName: $firstName or lastName: $lastName")
        }

        return middleName?.takeIf { it.isNotEmpty() }?.let {
            "$lastName ${firstName.first()}. ${it.first()}."
        } ?: "$lastName ${firstName.first()}."
    }

    override fun getAuthorities(): Collection<GrantedAuthority?> {
        return Optional.ofNullable(this.role)
            .map<Set<SimpleGrantedAuthority?>> {
                setOf(
                    SimpleGrantedAuthority(
                        this.role.name
                    )
                )
            }
            .orElse(emptySet<SimpleGrantedAuthority>())
    }

    override fun getPassword(): String = password

    override fun getUsername(): String = login


    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true

}