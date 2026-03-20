package com.diagorn.sparkathon.auth.mapper

import com.diagorn.sparkathon.auth.domain.Role
import com.diagorn.sparkathon.auth.dto.user.RoleDto
import org.springframework.stereotype.Component

@Component
class RoleMapper {
    fun toEntity(dto: RoleDto): Role = Role(
        id = dto.id,
        name = dto.name
    )

    fun toDto(entity: Role): RoleDto = RoleDto(
        id = entity.id,
        name = entity.name
    )
}