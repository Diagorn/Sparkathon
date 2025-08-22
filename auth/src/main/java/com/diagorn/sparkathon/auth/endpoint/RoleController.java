package com.diagorn.sparkathon.auth.endpoint;

import com.diagorn.sparkathon.auth.dto.user.RoleDTO;
import com.diagorn.sparkathon.auth.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for working with roles
 *
 * @author diagorn
 */
@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/")
    public ResponseEntity<List<RoleDTO>> findAll() {
        List<RoleDTO> all = roleService.findAllDTO();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/allowed")
    public ResponseEntity<List<RoleDTO>> findAllowedForCreation() {
        List<RoleDTO> all = roleService.findAllowedForCreationDTO();
        return ResponseEntity.ok(all);
    }

}
