package com.diagorn.sparkathon.auth.mapper;

import com.diagorn.sparkathon.auth.domain.User;
import com.diagorn.sparkathon.auth.dto.user.UserDTO;
import org.codehaus.plexus.util.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * User DTO mapper
 *
 * @author diagorn
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "fullName", expression = "java(getFullName(user))")
    UserDTO toDTO(User user);

    @Mapping(target = "telegramNickname", source = "telegramNickname")
    User toEntity(UserDTO userDTO);

    default String getFullName(User user) {
        if (StringUtils.isEmpty(user.getFirstName()) || StringUtils.isEmpty(user.getLastName())) {
            return null;
        }
        String fullName = String.format("%s %s.", user.getLastName(), user.getFirstName().charAt(0));
        if (StringUtils.isNotEmpty(user.getMiddleName())) {
            fullName += String.format(" %s.", user.getMiddleName().charAt(0));
        }
        return fullName;
    }
}
