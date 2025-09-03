package com.diagorn.sparkathon.auth.service;

import com.diagorn.sparkathon.auth.domain.Role;
import com.diagorn.sparkathon.auth.domain.User;
import com.diagorn.sparkathon.auth.dto.user.UserDTO;
import com.diagorn.sparkathon.auth.dto.user.UserRegistrationRequest;
import com.diagorn.sparkathon.auth.exception.BadRequestException;
import com.diagorn.sparkathon.auth.exception.NotFoundException;
import com.diagorn.sparkathon.auth.mapper.UserMapper;
import com.diagorn.sparkathon.auth.repo.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private RoleService roleService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private static final Long EXISTING_USER_ID = 1L;
    private static final Long NON_EXISTING_USER_ID = 999L;
    private static final Long EXISTING_ROLE_ID = 2L;
    private static final String EXISTING_LOGIN = "testuser";
    private static final String NON_EXISTING_LOGIN = "nonexisting";
    private static final String RAW_PASSWORD = "password123";
    private static final String ENCODED_PASSWORD = "encodedPassword123";

    @Test
    void loadUserByUsername_WithExistingLogin_ShouldReturnUserDetails() {
        // Given
        User user = createTestUser();
        given(userRepository.findByLogin(EXISTING_LOGIN))
                .willReturn(user);

        // When
        UserDetails result = userService.loadUserByUsername(EXISTING_LOGIN);

        // Then
        assertNotNull(result);
        assertEquals(EXISTING_LOGIN, result.getUsername());
        verify(userRepository).findByLogin(EXISTING_LOGIN);
    }

    @Test
    void loadUserByUsername_WithNonExistingLogin_ShouldThrowUsernameNotFoundException() {
        // Given
        given(userRepository.findByLogin(NON_EXISTING_LOGIN))
                .willReturn(null);

        // When & Then
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class,
                () -> userService.loadUserByUsername(NON_EXISTING_LOGIN));

        assertEquals("User by login nonexisting not found", exception.getMessage());
        verify(userRepository).findByLogin(NON_EXISTING_LOGIN);
    }

    @Test
    void saveNewUser_WithValidRequest_ShouldSaveAndReturnUserDTO() {
        // Given
        UserRegistrationRequest request = createRegistrationRequest();
        Role role = createTestRole();
        User userToSave = createUserToSave();
        User savedUser = createSavedUser();
        UserDTO expectedDTO = createUserDTO();

        given(roleService.getById(EXISTING_ROLE_ID))
                .willReturn(role);
        given(passwordEncoder.encode(RAW_PASSWORD))
                .willReturn(ENCODED_PASSWORD);
        given(userRepository.saveAndFlush(any(User.class)))
                .willReturn(savedUser);
        given(userMapper.toDTO(savedUser))
                .willReturn(expectedDTO);

        // When
        UserDTO result = userService.saveNewUser(request);

        // Then
        assertNotNull(result);
        assertEquals(expectedDTO, result);
        verify(roleService).getById(EXISTING_ROLE_ID);
        verify(passwordEncoder).encode(RAW_PASSWORD);
        verify(userRepository).saveAndFlush(any(User.class));
        verify(userMapper).toDTO(savedUser);
    }

    @Test
    void saveNewUser_WithDuplicateLoginOrEmail_ShouldThrowBadRequestException() {
        // Given
        UserRegistrationRequest request = createRegistrationRequest();
        Role role = createTestRole();

        given(roleService.getById(EXISTING_ROLE_ID))
                .willReturn(role);
        given(passwordEncoder.encode(RAW_PASSWORD))
                .willReturn(ENCODED_PASSWORD);
        given(userRepository.saveAndFlush(any(User.class)))
                .willThrow(DataIntegrityViolationException.class);

        // When & Then
        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> userService.saveNewUser(request));

        assertEquals("Could not save new user: person with such login or email already exists", exception.getMessage());
        verify(roleService).getById(EXISTING_ROLE_ID);
        verify(passwordEncoder).encode(RAW_PASSWORD);
        verify(userRepository).saveAndFlush(any(User.class));
        verify(userMapper, never()).toDTO(any());
    }

    @Test
    void updateUser_WithValidDTO_ShouldUpdateAndReturnUserDTO() {
        // Given
        UserDTO requestDTO = createUpdateRequestDTO();
        User existingUser = createSavedUser();
        User updatedUser = createUpdatedUser();
        UserDTO expectedDTO = createUpdatedUserDTO();

        given(userRepository.findById(EXISTING_USER_ID))
                .willReturn(Optional.of(existingUser));
        given(userMapper.toEntity(requestDTO))
                .willReturn(updatedUser);
        given(userRepository.save(updatedUser))
                .willReturn(updatedUser);
        given(userMapper.toDTO(updatedUser))
                .willReturn(expectedDTO);

        // When
        UserDTO result = userService.updateUser(requestDTO);

        // Then
        assertNotNull(result);
        assertEquals(expectedDTO, result);
        assertEquals(ENCODED_PASSWORD, updatedUser.getPassword()); // Password should be preserved
        verify(userRepository).findById(EXISTING_USER_ID);
        verify(userMapper).toEntity(requestDTO);
        verify(userRepository).save(updatedUser);
        verify(userMapper).toDTO(updatedUser);
    }

    @Test
    void updateUser_WithNonExistingUserId_ShouldThrowNotFoundException() {
        // Given
        UserDTO requestDTO = createUpdateRequestDTO();
        requestDTO.setId(NON_EXISTING_USER_ID);

        given(userRepository.findById(NON_EXISTING_USER_ID))
                .willReturn(Optional.empty());

        // When & Then
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> userService.updateUser(requestDTO));

        assertEquals("User not found", exception.getMessage());
        verify(userRepository).findById(NON_EXISTING_USER_ID);
        verify(userMapper, never()).toEntity(any());
        verify(userRepository, never()).save(any());
    }

    @Test
    void getById_WithExistingId_ShouldReturnUserDTO() {
        // Given
        User existingUser = createSavedUser();
        UserDTO expectedDTO = createUserDTO();

        given(userRepository.findById(EXISTING_USER_ID))
                .willReturn(Optional.of(existingUser));
        given(userMapper.toDTO(existingUser))
                .willReturn(expectedDTO);

        // When
        UserDTO result = userService.getById(EXISTING_USER_ID);

        // Then
        assertNotNull(result);
        assertEquals(expectedDTO, result);
        verify(userRepository).findById(EXISTING_USER_ID);
        verify(userMapper).toDTO(existingUser);
    }

    @Test
    void getById_WithNonExistingId_ShouldThrowNotFoundException() {
        // Given
        given(userRepository.findById(NON_EXISTING_USER_ID))
                .willReturn(Optional.empty());

        // When & Then
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> userService.getById(NON_EXISTING_USER_ID));

        assertEquals("User not found", exception.getMessage());
        verify(userRepository).findById(NON_EXISTING_USER_ID);
        verify(userMapper, never()).toDTO(any());
    }

    // Helper methods
    private User createTestUser() {
        User user = new User();
        user.setId(EXISTING_USER_ID);
        user.setLogin(EXISTING_LOGIN);
        user.setPassword(ENCODED_PASSWORD);
        user.setRole(createTestRole());
        return user;
    }

    private Role createTestRole() {
        Role role = new Role();
        role.setId(EXISTING_ROLE_ID);
        role.setName("PARTICIPANT");
        return role;
    }

    private UserRegistrationRequest createRegistrationRequest() {
        return UserRegistrationRequest.builder()
                .roleId(EXISTING_ROLE_ID)
                .login(EXISTING_LOGIN)
                .password(RAW_PASSWORD)
                .email("test@example.com")
                .telegramNickname("@testuser")
                .build();
    }

    private User createUserToSave() {
        return User.builder()
                .role(createTestRole())
                .login(EXISTING_LOGIN)
                .password(ENCODED_PASSWORD)
                .email("test@example.com")
                .telegramNickname("@testuser")
                .build();
    }

    private User createSavedUser() {
        User user = createUserToSave();
        user.setId(EXISTING_USER_ID);
        return user;
    }

    private User createUpdatedUser() {
        User user = createSavedUser();
        user.setEmail("updated@example.com");
        user.setTelegramNickname("@updateduser");
        return user;
    }

    private UserDTO createUserDTO() {
        UserDTO dto = new UserDTO();
        dto.setId(EXISTING_USER_ID);
        dto.setLogin(EXISTING_LOGIN);
        dto.setEmail("test@example.com");
        dto.setTelegramNickname("@testuser");
        return dto;
    }

    private UserDTO createUpdateRequestDTO() {
        UserDTO dto = new UserDTO();
        dto.setId(EXISTING_USER_ID);
        dto.setLogin(EXISTING_LOGIN);
        dto.setEmail("updated@example.com");
        dto.setTelegramNickname("@updateduser");
        return dto;
    }

    private UserDTO createUpdatedUserDTO() {
        UserDTO dto = new UserDTO();
        dto.setId(EXISTING_USER_ID);
        dto.setLogin(EXISTING_LOGIN);
        dto.setEmail("updated@example.com");
        dto.setTelegramNickname("@updateduser");
        return dto;
    }
}