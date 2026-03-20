package com.diagorn.sparkathon.auth.web

import com.diagorn.sparkathon.auth.dto.user.RoleDto
import com.diagorn.sparkathon.auth.service.RoleService
import com.diagorn.sparkathon.common.AbstractWebErrorHandler
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * REST controller for working with roles
 *
 * @author diagorn
 */
@RestController
@RequestMapping("/api/v1/roles")
@Tag(name = "Roles", description = "Roles management API")
@RequiredArgsConstructor
class RoleController(
    private val roleService: RoleService
) : AbstractWebErrorHandler() {

    @GetMapping("/")
    @Operation(summary = "Get all roles")
    fun findAll(): ResponseEntity<List<RoleDto>> {
        val all = roleService.findAllDTO()
        return ResponseEntity.ok(all)
    }

    @GetMapping("/allowed")
    @Operation(summary = "Get all roles with which a user can be created")
    fun findAllowedForCreation(): ResponseEntity<List<RoleDto>> {
        val all = roleService.findAllowedForCreationDTO()
        return ResponseEntity.ok(all)
    }
}