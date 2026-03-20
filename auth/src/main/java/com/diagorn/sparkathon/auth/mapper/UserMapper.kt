package com.diagorn.sparkathon.auth.mapper

import com.diagorn.sparkathon.auth.domain.User
import com.diagorn.sparkathon.auth.dto.kafka.NewUserContactsEvent
import com.diagorn.sparkathon.auth.dto.user.UserDto
import com.diagorn.sparkathon.auth.dto.user.UserRegistrationRequest
import com.diagorn.sparkathon.auth.service.RoleService
import com.diagorn.sparkathon.common.exception.BadRequestException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class UserMapper(
    private val roleMapper: RoleMapper,
    private val roleService: RoleService,
    private val passwordEncoder: PasswordEncoder,
) {

    fun toDto(entity: User): UserDto = UserDto(
        id = entity.id,
        role = roleMapper.toDto(entity.role),
        firstName = entity.firstName,
        middleName = entity.middleName,
        lastName = entity.lastName,
        login = entity.login,
        email = entity.email,
        telegramNickname = entity.telegramNickname,
        fullName = entity.fullName()
    )

    fun toEntity(dto: UserDto, password: String): User = User(
        id = dto.id,
        role = roleMapper.toEntity(dto.role),
        firstName = dto.firstName,
        middleName = dto.middleName,
        lastName = dto.lastName,
        login = dto.login,
        password = password,
        email = dto.email,
        telegramNickname = dto.telegramNickname
    )

    fun toUserContactsEvent(user: User) = NewUserContactsEvent(
        id = user.id,
        telegramNickname = user.telegramNickname,
        email = user.email,
        createdAt = LocalDateTime.now()
    )

    fun toEntity(request: UserRegistrationRequest) = User(
        role = roleService.getById(request.roleId),
        login = request.login,
        password = passwordEncoder.encode(request.password),
        email = request.email,
        telegramNickname = request.telegramNickname,
        firstName = request.firstName,
        middleName = request.middleName,
        lastName = request.lastName
    )

}