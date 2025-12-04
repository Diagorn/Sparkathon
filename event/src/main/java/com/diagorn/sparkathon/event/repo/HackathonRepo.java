package com.diagorn.sparkathon.event.repo;

import com.diagorn.sparkathon.event.model.hackathon.Hackathon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Hackathon JPA repository
 *
 * @author diagorn
 */
@Repository
public interface HackathonRepo extends JpaRepository<Hackathon, Long> {
}
