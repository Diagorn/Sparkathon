package com.diagorn.sparkathon.auth.service;

import com.diagorn.sparkathon.auth.domain.Role;
import com.diagorn.sparkathon.auth.dto.user.RoleDTO;
import com.diagorn.sparkathon.common.exception.NotFoundException;
import com.diagorn.sparkathon.auth.mapper.RoleMapper;
import com.diagorn.sparkathon.auth.repo.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private RoleMapper roleMapper;

    @InjectMocks
    private RoleService roleService;

    private static final Long EXISTING_ROLE_ID = 1L;
    private static final Long NON_EXISTING_ROLE_ID = 999L;

    @Test
    void getById_WithExistingId_ShouldReturnRole() {
        // Given
        Role expectedRole = new Role();
        expectedRole.setId(EXISTING_ROLE_ID);
        expectedRole.setName("PARTICIPANT");

        given(roleRepository.findById(EXISTING_ROLE_ID))
                .willReturn(Optional.of(expectedRole));

        // When
        Role result = roleService.getById(EXISTING_ROLE_ID);

        // Then
        assertNotNull(result);
        assertEquals(EXISTING_ROLE_ID, result.getId());
        verify(roleRepository).findById(EXISTING_ROLE_ID);
    }

    @Test
    void getById_WithNonExistingId_ShouldThrowNotFoundException() {
        // Given
        given(roleRepository.findById(NON_EXISTING_ROLE_ID))
                .willReturn(Optional.empty());

        // When & Then
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> roleService.getById(NON_EXISTING_ROLE_ID));

        assertEquals("Could not find user role with id = 999", exception.getMessage());
        verify(roleRepository).findById(NON_EXISTING_ROLE_ID);
    }

    @Test
    void findAll_ShouldReturnAllRoles() {
        // Given
        Role role1 = new Role();
        role1.setId(1L);
        role1.setName("ROLE_USER");

        Role role2 = new Role();
        role2.setId(2L);
        role2.setName("ROLE_ADMIN");

        List<Role> expectedRoles = List.of(role1, role2);

        given(roleRepository.findAll())
                .willReturn(expectedRoles);

        // When
        List<Role> result = roleService.findAll();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(expectedRoles, result);
        verify(roleRepository).findAll();
    }

    @Test
    void findAllDTO_ShouldReturnAllRolesAsDTO() {
        // Given
        Role role1 = new Role();
        role1.setId(1L);
        role1.setName("ROLE_USER");

        Role role2 = new Role();
        role2.setId(2L);
        role2.setName("ROLE_ADMIN");

        List<Role> roles = List.of(role1, role2);

        RoleDTO roleDTO1 = new RoleDTO();
        roleDTO1.setId(1L);
        roleDTO1.setName("ROLE_USER");

        RoleDTO roleDTO2 = new RoleDTO();
        roleDTO2.setId(2L);
        roleDTO2.setName("ROLE_ADMIN");

        List<RoleDTO> expectedDTOs = List.of(roleDTO1, roleDTO2);

        given(roleRepository.findAll())
                .willReturn(roles);
        given(roleMapper.toDTO(role1))
                .willReturn(roleDTO1);
        given(roleMapper.toDTO(role2))
                .willReturn(roleDTO2);

        // When
        List<RoleDTO> result = roleService.findAllDTO();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(expectedDTOs, result);
        verify(roleRepository).findAll();
        verify(roleMapper).toDTO(role1);
        verify(roleMapper).toDTO(role2);
    }

    @Test
    void findAllowedForCreation_ShouldReturnAllowedRoles() {
        // Given
        Role allowedRole1 = new Role();
        allowedRole1.setId(1L);
        allowedRole1.setName("ROLE_USER");
        allowedRole1.setIsAllowedForCreation(true);

        Role allowedRole2 = new Role();
        allowedRole2.setId(2L);
        allowedRole2.setName("ROLE_MODERATOR");
        allowedRole2.setIsAllowedForCreation(true);

        Role notAllowedRole = new Role();
        notAllowedRole.setId(3L);
        notAllowedRole.setName("ROLE_ADMIN");
        notAllowedRole.setIsAllowedForCreation(false);

        List<Role> expectedRoles = List.of(allowedRole1, allowedRole2);

        given(roleRepository.findAllByIsAllowedForCreation(true))
                .willReturn(expectedRoles);

        // When
        List<Role> result = roleService.findAllowedForCreation();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(Role::getIsAllowedForCreation));
        verify(roleRepository).findAllByIsAllowedForCreation(true);
    }

    @Test
    void findAllowedForCreationDTO_ShouldReturnAllowedRolesAsDTO() {
        // Given
        Role allowedRole1 = new Role();
        allowedRole1.setId(1L);
        allowedRole1.setName("ROLE_USER");
        allowedRole1.setIsAllowedForCreation(true);

        Role allowedRole2 = new Role();
        allowedRole2.setId(2L);
        allowedRole2.setName("ROLE_MODERATOR");
        allowedRole2.setIsAllowedForCreation(true);

        List<Role> allowedRoles = List.of(allowedRole1, allowedRole2);

        RoleDTO roleDTO1 = new RoleDTO();
        roleDTO1.setId(1L);
        roleDTO1.setName("ROLE_USER");

        RoleDTO roleDTO2 = new RoleDTO();
        roleDTO2.setId(2L);
        roleDTO2.setName("ROLE_MODERATOR");

        List<RoleDTO> expectedDTOs = List.of(roleDTO1, roleDTO2);

        given(roleRepository.findAllByIsAllowedForCreation(true))
                .willReturn(allowedRoles);
        given(roleMapper.toDTO(allowedRole1))
                .willReturn(roleDTO1);
        given(roleMapper.toDTO(allowedRole2))
                .willReturn(roleDTO2);

        // When
        List<RoleDTO> result = roleService.findAllowedForCreationDTO();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(expectedDTOs, result);
        verify(roleRepository).findAllByIsAllowedForCreation(true);
        verify(roleMapper).toDTO(allowedRole1);
        verify(roleMapper).toDTO(allowedRole2);
    }

    @Test
    void findAll_WithEmptyRepository_ShouldReturnEmptyList() {
        // Given
        given(roleRepository.findAll())
                .willReturn(List.of());

        // When
        List<Role> result = roleService.findAll();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(roleRepository).findAll();
    }

    @Test
    void findAllowedForCreation_WithNoAllowedRoles_ShouldReturnEmptyList() {
        // Given
        given(roleRepository.findAllByIsAllowedForCreation(true))
                .willReturn(List.of());

        // When
        List<Role> result = roleService.findAllowedForCreation();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(roleRepository).findAllByIsAllowedForCreation(true);
    }

    @Test
    void findAllDTO_WithEmptyRepository_ShouldReturnEmptyList() {
        // Given
        given(roleRepository.findAll())
                .willReturn(List.of());

        // When
        List<RoleDTO> result = roleService.findAllDTO();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(roleRepository).findAll();
    }

    @Test
    void findAllowedForCreationDTO_WithNoAllowedRoles_ShouldReturnEmptyList() {
        // Given
        given(roleRepository.findAllByIsAllowedForCreation(true))
                .willReturn(List.of());

        // When
        List<RoleDTO> result = roleService.findAllowedForCreationDTO();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(roleRepository).findAllByIsAllowedForCreation(true);
    }
}