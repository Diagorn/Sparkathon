package com.diagorn.sparkathon.auth.endpoint;

import com.diagorn.sparkathon.auth.dto.user.UserDTO;
import com.diagorn.sparkathon.auth.dto.user.UserRegistrationRequest;
import com.diagorn.sparkathon.auth.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * REST controller for working with users
 *
 * @author diagorn
 */
@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users", description = "User management API")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/")
    @Operation(summary = "Save new user")
    public ResponseEntity<UserDTO> saveNewUser(
            @Valid
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User object to create", required = true)
            UserRegistrationRequest request) {
        UserDTO result = userService.saveNewUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PatchMapping("/")
    @Operation(summary = "Edit user")
    public ResponseEntity<UserDTO> editUser(
            @Valid
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Edited user", required = true)
            UserDTO userDTO) {
        UserDTO result = userService.updateUser(userDTO);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID")
    public ResponseEntity<UserDTO> getById(
            @PathVariable
            @Parameter(description = "User ID")
            Long id) {
        UserDTO result = userService.getById(id);
        return ResponseEntity.ok(result);
    }
}
