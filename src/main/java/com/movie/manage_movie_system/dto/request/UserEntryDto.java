package com.movie.manage_movie_system.dto.request;

import com.movie.manage_movie_system.enums.UserRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserEntryDto {
    private String username;
    private String password;
    private String email;
    private UserRole role;
}
