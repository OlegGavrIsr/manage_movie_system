package com.movie.manage_movie_system.repository;

import com.movie.manage_movie_system.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query(value = "select exists (select * from booking where showtime_id = :showtimeId and seat_number = :seatNumber)" , nativeQuery = true)
    Boolean isExistBookingForThisShowtimeAndSeatNumber(@Param("showtimeId") Long showtimeId, Integer seatNumber);
}
