package com.diagorn.sparkathon.auth.dto.user;

import lombok.Data;

/**
 * Role DTO
 *
 * @author diagorn
 */
@Data
public class RoleDTO {
    /**
     * Role ID
     */
    private Long id;
    /**
     * Role name
     */
    private String name;
}
