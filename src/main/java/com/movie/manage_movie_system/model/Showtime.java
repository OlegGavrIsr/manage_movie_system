package com.movie.manage_movie_system.model;

import com.movie.manage_movie_system.enums.Theater;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Showtime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Movie movie;

    @Enumerated(value = EnumType.STRING)
    private Theater theater;

    private LocalDateTime start_time;

    private LocalDateTime end_time;


}
