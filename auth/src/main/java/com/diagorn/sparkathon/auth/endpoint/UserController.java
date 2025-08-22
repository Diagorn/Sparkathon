package com.diagorn.sparkathon.auth.endpoint;

import com.diagorn.sparkathon.auth.dto.user.UserDTO;
import com.diagorn.sparkathon.auth.dto.user.UserRegistrationRequest;
import com.diagorn.sparkathon.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * REST controller for working with users
 *
 * @author diagorn
 */
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/")
    public ResponseEntity<UserDTO> saveNewUser(@Valid @RequestBody UserRegistrationRequest request) {
        UserDTO result = userService.saveNewUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PatchMapping("/")
    public ResponseEntity<UserDTO> editUser(@Valid @RequestBody UserDTO userDTO) {
        UserDTO result = userService.updateUser(userDTO);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable Long id) {
        UserDTO result = userService.getById(id);
        return ResponseEntity.ok(result);
    }
}
