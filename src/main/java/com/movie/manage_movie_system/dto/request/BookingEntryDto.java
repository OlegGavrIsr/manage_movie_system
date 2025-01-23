package com.movie.manage_movie_system.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookingEntryDto {
    private Long movieId;
    private Long showtimeId;
    private Integer seat_number;
    private Integer price;

}
