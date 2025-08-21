package com.diagorn.sparkathon.auth.repo;

import com.diagorn.sparkathon.auth.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for working with user roles
 *
 * @author diagorn
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    /**
     * Find role by its nme
     *
     * @param name - role name
     * @return role
     */
    Role findByName(String name);
}
