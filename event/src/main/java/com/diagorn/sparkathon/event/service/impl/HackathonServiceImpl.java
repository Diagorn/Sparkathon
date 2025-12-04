package com.diagorn.sparkathon.event.service.impl;

import com.diagorn.sparkathon.event.dto.HackathonDto;
import com.diagorn.sparkathon.event.dto.HackathonRequest;
import com.diagorn.sparkathon.event.dto.HackathonSearchRequest;
import com.diagorn.sparkathon.event.dto.HackathonStatusChangeRequest;
import com.diagorn.sparkathon.event.repo.HackathonRepo;
import com.diagorn.sparkathon.event.service.HackathonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class HackathonServiceImpl implements HackathonService {

    private final HackathonRepo hackathonRepo;

    @Override
    public Page<HackathonDto> find(HackathonSearchRequest searchRequest, Pageable pageable) {
        throw new IllegalStateException("Not yet implemented");
    }

    @Override
    public HackathonDto findById(Long id) {
        throw new IllegalStateException("Not yet implemented");
    }

    @Override
    public HackathonDto createNewHackathon(HackathonRequest request) {
        throw new IllegalStateException("Not yet implemented");
    }

    @Override
    public HackathonDto updateHackathon(Long hackathonId, HackathonDto hackathonDto) {
        throw new IllegalStateException("Not yet implemented");
    }

    @Override
    public HackathonDto changeStatus(Long hackathonId, HackathonStatusChangeRequest newStatus) {
        throw new IllegalStateException("Not yet implemented");
    }

    @Override
    public HackathonDto delete(Long hackathonId) {
        throw new IllegalStateException("Not yet implemented");
    }
}
