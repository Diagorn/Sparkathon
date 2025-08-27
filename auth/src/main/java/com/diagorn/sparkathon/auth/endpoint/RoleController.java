package com.diagorn.sparkathon.auth.endpoint;

import com.diagorn.sparkathon.auth.dto.user.RoleDTO;
import com.diagorn.sparkathon.auth.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Roles", description = "Roles management API")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/")
    @Operation(summary = "Get all roles")
    public ResponseEntity<List<RoleDTO>> findAll() {
        List<RoleDTO> all = roleService.findAllDTO();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/allowed")
    @Operation(summary = "Get all roles with which a user can be created")
    public ResponseEntity<List<RoleDTO>> findAllowedForCreation() {
        List<RoleDTO> all = roleService.findAllowedForCreationDTO();
        return ResponseEntity.ok(all);
    }

}
