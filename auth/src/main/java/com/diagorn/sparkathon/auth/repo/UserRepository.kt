package com.diagorn.sparkathon.auth.repo

import com.diagorn.sparkathon.auth.domain.User
import com.diagorn.sparkathon.auth.utils.Messages
import com.diagorn.sparkathon.common.exception.NotFoundException
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Repository for working with users
 *
 * @author diagorn
 */
interface UserRepository: JpaRepository<User, Long> {
    /**
     * Find user by his login
     *
     * @param login - user login
     * @return user
     */
    fun findByLogin(login: String): User?

    /**
     * Get user by login or throw
     *
     * @param login - user login
     * @return user
     */
    fun getByLogin(login: String): User = findByLogin(login)
        ?: throw NotFoundException(Messages.userNotFoundByLogin(login))

    override fun getById(id: Long) = findById(id).orElseThrow {
        NotFoundException("")
    }
}