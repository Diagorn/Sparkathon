package com.diagorn.sparkathon.auth.repo

import com.diagorn.sparkathon.auth.domain.Role
import com.diagorn.sparkathon.auth.utils.Messages
import com.diagorn.sparkathon.common.exception.NotFoundException
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<Role, Long> {
    /**
     * Find role by its nme
     *
     * @param name - role name
     * @return role
     */
    fun findByName(name: String?): Role?

    /**
     * Find all roles that are allowed or not allowed for creation
     * @param isAllowedForCreation - if roles should be allowed
     * @return list of roles
     */
    fun findAllByAllowedForCreation(isAllowedForCreation: Boolean = true): List<Role>

    override fun getById(id: Long) = findById(id).orElseThrow {
        NotFoundException(Messages.roleNotFoundById(id))
    }
}