package com.diagorn.sparkathon.auth.repo;

import com.diagorn.sparkathon.auth.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for working with users
 *
 * @author diagorn
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Find user by his login
     *
     * @param login - user login
     * @return user
     */
    User findByLogin(String login);
}
