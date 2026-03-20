package com.diagorn.sparkathon.auth.service

import com.diagorn.sparkathon.auth.domain.Role
import com.diagorn.sparkathon.auth.dto.user.RoleDto
import com.diagorn.sparkathon.auth.mapper.RoleMapper
import com.diagorn.sparkathon.auth.repo.RoleRepository
import org.springframework.stereotype.Service

/**
 * Service for working with roles
 *
 * @author diagorn
 */
@Service
class RoleService(
    private val roleRepository: RoleRepository,
    private val roleMapper: RoleMapper,
) {

    /**
     * Find role by ID
     * @param id - role id
     * @return role
     */
    fun getById(id: Long) = roleRepository.getById(id)

    /**
     * Find all roles
     * @return all roles
     */
    fun findAll(): List<Role> = roleRepository.findAll()

    /**
     * Find all roles and convert them to DTO
     * @return all roles DTO format
     */
    fun findAllDTO(): List<RoleDto> = findAll().map{ roleMapper.toDto(it) }

    /**
     * Find roles that are allowed for creation
     * @return all roles that are allowed for creation
     */
    fun findAllowedForCreation(): List<Role> = roleRepository.findAllByAllowedForCreation()

    /**
     * Find roles that are allowed for creation DTO format
     * @return all roles that are allowed for creation DTO format
     */
    fun findAllowedForCreationDTO(): List<RoleDto> = findAllowedForCreation().map { roleMapper.toDto(it) }

}