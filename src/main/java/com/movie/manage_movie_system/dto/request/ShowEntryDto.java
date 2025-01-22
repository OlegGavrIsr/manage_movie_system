package com.movie.manage_movie_system.dto.request;

import com.movie.manage_movie_system.enums.Theater;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ShowEntryDto {
    private Long movieId;

    @Enumerated(value = EnumType.STRING)
    private Theater theater;

    private LocalDateTime start_time;
    private LocalDateTime end_time;

}
