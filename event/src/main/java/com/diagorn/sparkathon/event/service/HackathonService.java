package com.diagorn.sparkathon.event.service;

import com.diagorn.sparkathon.event.dto.HackathonDto;
import com.diagorn.sparkathon.event.dto.HackathonRequest;
import com.diagorn.sparkathon.event.dto.HackathonSearchRequest;
import com.diagorn.sparkathon.event.dto.HackathonStatusChangeRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Hackathon management service
 *
 * @author diagorn
 */
public interface HackathonService {

    /**
     * Find hackathons
     *
     * @param searchRequest - search request params
     * @param pageable      - page params
     * @return hackathons page
     */
    Page<HackathonDto> find(HackathonSearchRequest searchRequest, Pageable pageable);

    /**
     * Find hackathon by ID
     *
     * @param id - hackathon ID
     * @return hackathon DTO
     */
    HackathonDto findById(Long id);

    /**
     * Create new hackathon
     *
     * @param request - hackathon creation request
     * @return hackathon DTO
     */
    HackathonDto createNewHackathon(HackathonRequest request);

    /**
     * Update hackathon
     *
     * @param hackathonId  - hackathon ID
     * @param hackathonDto - new hackathon version
     * @return updated hackathon version
     */
    HackathonDto updateHackathon(Long hackathonId, HackathonDto hackathonDto);

    /**
     * Update hackathon status
     *
     * @param hackathonId - hackathon ID
     * @param newStatus   - new status to be put into database
     * @return updated hackathon
     */
    HackathonDto changeStatus(Long hackathonId, HackathonStatusChangeRequest newStatus);

    /**
     * Logically delete hackathon
     *
     * @param hackathonId - hackathon ID
     * @return deleted hackathon
     */
    HackathonDto delete(Long hackathonId);
}
