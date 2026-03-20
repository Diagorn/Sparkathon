package com.diagorn.sparkathon.auth.web

import com.diagorn.sparkathon.auth.dto.user.UserDto
import com.diagorn.sparkathon.auth.dto.user.UserRegistrationRequest
import com.diagorn.sparkathon.auth.service.UserService
import com.diagorn.sparkathon.auth.utils.Messages
import com.diagorn.sparkathon.common.AbstractWebErrorHandler
import com.diagorn.sparkathon.common.exception.BadRequestException
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * REST controller for working with users
 *
 * @author diagorn
 */
@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users", description = "User management API")
@RequiredArgsConstructor
class UserController(
    private val userService: UserService
) : AbstractWebErrorHandler() {

    @PostMapping("/")
    @Operation(summary = "Save new user")
    fun saveNewUser(
        @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "User object to create",
            required = true
        ) request: @Valid UserRegistrationRequest
    ): ResponseEntity<UserDto> {
        val result = userService.saveNewUser(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(result)
    }

    @PatchMapping("/")
    @Operation(summary = "Edit user")
    fun editUser(
        @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Edited user",
            required = true
        ) userDTO: @Valid UserDto
    ): ResponseEntity<UserDto> {
        val result = userService.updateUser(userDTO)
        return ResponseEntity.ok(result)
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID")
    fun getById(
        @PathVariable(name = "id") @Parameter(description = "User ID") id: Long?
    ): ResponseEntity<UserDto> {
        val userId = id ?: throw BadRequestException(Messages.userIdRequired())
        val result = userService.getById(userId)
        return ResponseEntity.ok(result)
    }
}