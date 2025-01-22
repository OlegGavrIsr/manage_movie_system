package com.movie.manage_movie_system.model;

import com.movie.manage_movie_system.enums.Genre;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @Enumerated(value = EnumType.STRING)
    private Genre genre;

    private Integer duration;//we can use Duration if you need

    @Min(0)
    @Max(5)
    private Integer rating;

    private LocalDate release_year;


}