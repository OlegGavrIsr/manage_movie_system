package com.movie.manage_movie_system.dto.request;

import com.movie.manage_movie_system.model.Movie;
import com.movie.manage_movie_system.model.Showtime;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
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
