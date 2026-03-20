package com.diagorn.sparkathon.auth.service

import com.diagorn.sparkathon.auth.client.KafkaClient
import com.diagorn.sparkathon.auth.config.properties.KafkaTopicProperties
import com.diagorn.sparkathon.auth.dto.user.UserDto
import com.diagorn.sparkathon.auth.dto.user.UserRegistrationRequest
import com.diagorn.sparkathon.auth.mapper.UserMapper
import com.diagorn.sparkathon.auth.repo.UserRepository
import com.diagorn.sparkathon.auth.utils.Messages
import com.diagorn.sparkathon.common.exception.BadRequestException
import jakarta.transaction.Transactional
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

/**
 * User service
 *
 * @author diagorn
 */
@Service
class UserService(
    private val userMapper: UserMapper,
    private val userRepository: UserRepository,
    private val kafkaClient: KafkaClient,
    private val kafkaTopicProperties: KafkaTopicProperties,
) : UserDetailsService {

    /**
     * Load user by login
     *
     * @param login - login
     * @return user
     * @throws UsernameNotFoundException if such login is absent in db
     */
    override fun loadUserByUsername(login: String) = userRepository.getByLogin(login)

    /**
     * Save a new user
     * @param request - new user request
     * @return new user
     */
    @Transactional
    fun saveNewUser(request: UserRegistrationRequest): UserDto {
        var user = userMapper.toEntity(request)

        try {
            user = userRepository.saveAndFlush(user)
        } catch (e: DataIntegrityViolationException) {
            throw BadRequestException(Messages.userCreationFailed())
        }

        kafkaClient.sendUserContacts(
            data = userMapper.toUserContactsEvent(user),
            topic = kafkaTopicProperties.newUser
        )

        return userMapper.toDto(user)
    }

    /**
     * Update a user
     * @param userDto - updated user fields
     * @return
     */
    @Transactional
    fun updateUser(userDto: UserDto): UserDto {
        val dbUser = userRepository.getById(userDto.id)
        val user = userMapper.toEntity(userDto, dbUser.password)

        user.password = dbUser.getPassword()
        user.role = dbUser.role
        userRepository.save(user)

        kafkaClient.sendUserContacts(
            data = userMapper.toUserContactsEvent(user),
            topic = kafkaTopicProperties.newUser
        )

        return userMapper.toDto(user)
    }

    /**
     * Find user by id
     * @param id - user ID
     * @return found user
     */
    fun getById(id: Long): UserDto = userMapper.toDto(userRepository.getById(id))
}