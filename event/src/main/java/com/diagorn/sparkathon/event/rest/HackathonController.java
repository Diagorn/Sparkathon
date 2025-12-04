package com.diagorn.sparkathon.event.rest;

import com.diagorn.sparkathon.event.dto.*;
import com.diagorn.sparkathon.event.service.HackathonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.web.bind.annotation.*;

import static com.diagorn.sparkathon.event.utils.HeaderUtils.ROLE_HEADER_NAME;

@RestController
@RequestMapping("/api/v1/hackathon")
@RequiredArgsConstructor
public class HackathonController {

    private final HackathonService hackathonService;

    @GetMapping("/search")
    public ResponseEntity<Page<HackathonSearchResponse>> search(
            @RequestBody HackathonSearchRequest request,
            Pageable pageable
    ) {
        throw new IllegalStateException("Not yet implemented");
    }

    @GetMapping("/{id}")
    public ResponseEntity<HackathonDto> findById(@PathVariable String id) {
        throw new IllegalStateException("Not yet implemented");
    }

    @PostMapping("/")
    public ResponseEntity<HackathonDto> create(
            @RequestBody HackathonRequest request,
            @Header(name = ROLE_HEADER_NAME) String userRole
    ) {
        throw new IllegalStateException("Not yet implemented");
    }

    @PutMapping("/{id}")
    public ResponseEntity<HackathonDto> update(
            @RequestBody HackathonRequest request,
            @PathVariable Long id,
            @Header(name = ROLE_HEADER_NAME) String userRole
    ) {
        throw new IllegalStateException("Not yet implemented");
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<HackathonDto> changeStatus(
            @RequestBody HackathonStatusChangeRequest request,
            @PathVariable Long id
    ) {
        throw new IllegalStateException("Not yet implemented");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HackathonDto> deleteById(
            @PathVariable Long id,
            @Header(name = ROLE_HEADER_NAME) String userRole
    ) {
        throw new IllegalStateException("Not yet implemented");
    }
}
