package com.diagorn.sparkathon.auth.service;

import com.diagorn.sparkathon.auth.domain.Role;
import com.diagorn.sparkathon.auth.dto.user.RoleDTO;
import com.diagorn.sparkathon.common.exception.NotFoundException;
import com.diagorn.sparkathon.auth.mapper.RoleMapper;
import com.diagorn.sparkathon.auth.repo.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Role service
 *
 * @author diagorn
 */
@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    /**
     * Find role by ID
     * @param id - role id
     * @return role
     */
    public Role getById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Could not find user role with id = %d", id)
                ));
    }

    /**
     * Find all roles
     * @return all roles
     */
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    /**
     * Find all roles and convert them to DTO
     * @return all roles DTO format
     */
    public List<RoleDTO> findAllDTO() {
        return this.findAll().stream()
                .map(roleMapper::toDTO)
                .toList();
    }

    /**
     * Find roles that are allowed for creation
     * @return all roles that are allowed for creation
     */
    public List<Role> findAllowedForCreation() {
        return roleRepository.findAllByIsAllowedForCreation(true);
    }

    /**
     * Find roles that are allowed for creation DTO format
     * @return all roles that are allowed for creation DTO format
     */
    public List<RoleDTO> findAllowedForCreationDTO() {
        return this.findAllowedForCreation().stream()
                .map(roleMapper::toDTO)
                .toList();
    }
}
