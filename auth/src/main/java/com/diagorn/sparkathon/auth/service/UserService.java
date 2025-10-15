package com.diagorn.sparkathon.auth.service;

import com.diagorn.sparkathon.auth.client.KafkaClient;
import com.diagorn.sparkathon.auth.config.properties.KafkaTopicProperties;
import com.diagorn.sparkathon.auth.domain.User;
import com.diagorn.sparkathon.auth.dto.kafka.NewUserContactsEvent;
import com.diagorn.sparkathon.auth.dto.user.UserDTO;
import com.diagorn.sparkathon.auth.dto.user.UserRegistrationRequest;
import com.diagorn.sparkathon.auth.exception.BadRequestException;
import com.diagorn.sparkathon.auth.exception.NotFoundException;
import com.diagorn.sparkathon.auth.mapper.UserMapper;
import com.diagorn.sparkathon.auth.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * User service
 *
 * @author diagorn
 */
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final RoleService roleService;

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final KafkaClient kafkaClient;

    private final KafkaTopicProperties kafkaTopicProperties;
    /**
     * Load user by login
     *
     * @param login - login
     * @return user
     * @throws UsernameNotFoundException if such login is absent in db
     */
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return Optional.ofNullable(userRepository.findByLogin(login))
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("User by login %s not found", login)
                ));
    }

    /**
     * Save a new user
     * @param request - new user request
     * @return new user
     */
    @Transactional
    public UserDTO saveNewUser(UserRegistrationRequest request) {
        User user = User.builder()
                .role(roleService.getById(request.getRoleId()))
                .login(request.getLogin())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .telegramNickname(request.getTelegramNickname())
                .build();

        try {
            user = userRepository.saveAndFlush(user);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Could not save new user: person with such login or email already exists");
        }

        kafkaClient.sendUserContacts(getUserContacts(user), kafkaTopicProperties.getNewUser());

        return userMapper.toDTO(user);
    }

    /**
     * Update a user
     * @param userDTO - updated user fields
     * @return
     */
    @Transactional
    public UserDTO updateUser(UserDTO userDTO) {
        User dbUser = get(userDTO.getId());
        User user = userMapper.toEntity(userDTO);

        user.setPassword(dbUser.getPassword());
        user.setRole(dbUser.getRole());
        userRepository.save(user);

        kafkaClient.sendUserContacts(getUserContacts(user), kafkaTopicProperties.getEditUser());

        return userMapper.toDTO(user);
    }

    /**
     * Find user by id
     * @param id - user ID
     * @return found user
     */
    public UserDTO getById(Long id) {
        return userMapper.toDTO(get(id));
    }

    /**
     * Get user by ID
     * Throws NotFoundException if ID is not found in the database
     * @param id - user ID
     * @return found user
     */
    private User get(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    private NewUserContactsEvent getUserContacts(User user) {
        return NewUserContactsEvent.builder()
                .id(user.getId())
                .telegramNickname(user.getTelegramNickname())
                .email(user.getEmail())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
