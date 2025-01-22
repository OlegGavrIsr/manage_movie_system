package com.movie.manage_movie_system.repository;

import com.movie.manage_movie_system.enums.Theater;
import com.movie.manage_movie_system.model.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {
    @Query(value = "select * from shows where theater = :theater" , nativeQuery = true)
    List<Showtime> getAllShowsOfTheater(@Param("theater") Theater theater);

    @Query(value = "select * from shows where moview_id = :movieId" , nativeQuery = true)
    List<Showtime> getAllShowsOfMovie(@Param("movieId") Long movieId);
}
