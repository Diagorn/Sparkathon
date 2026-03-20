package com.diagorn.sparkathon.auth.mapper;

import com.diagorn.sparkathon.auth.domain.Role;
import com.diagorn.sparkathon.auth.dto.user.RoleDTO;
import org.mapstruct.Mapper;

/**
 * Role mapper
 *
 * @author diagorn
 */
@Mapper(componentModel = "spring")
public interface RoleMapper {

    Role toEntity(RoleDTO roleDTO);

    RoleDTO toDTO(Role role);
}
