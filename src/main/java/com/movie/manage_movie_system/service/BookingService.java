package com.movie.manage_movie_system.service;

import com.movie.manage_movie_system.dto.request.BookingEntryDto;
import com.movie.manage_movie_system.enums.Theater;
import com.movie.manage_movie_system.model.Booking;
import com.movie.manage_movie_system.model.Movie;
import com.movie.manage_movie_system.model.Showtime;
import com.movie.manage_movie_system.model.User;
import com.movie.manage_movie_system.repository.BookingRepository;
import com.movie.manage_movie_system.repository.MovieRepository;
import com.movie.manage_movie_system.repository.ShowtimeRepository;
import com.movie.manage_movie_system.repository.UserRepository;
import lombok.Builder;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;


@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
@Service
@Transactional
public class BookingService {

    private static final Logger logger = LoggerFactory.getLogger(BookingService.class);

    @Value("#{${maxSeats.byTheater}}")
    private Map<String, Integer> maxSeatsByTheaterPerSubjectMap;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ShowtimeRepository showtimeRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;

    public String saveShowtime(BookingEntryDto bookingEntryDto) {

        //get Movie by movieId
        Optional<Movie> movieOpt = movieRepository.findById(bookingEntryDto.getMovieId());
        if (movieOpt.isEmpty()) {
            throw new RuntimeException("Movie does not Exists");
        }
        Movie movie = movieOpt.get();

        //get Showtime by showtimeId
        Optional<Showtime> showtimeOpt = showtimeRepository.findById(bookingEntryDto.getShowtimeId());
        if (showtimeOpt.isEmpty()) {
            throw new RuntimeException("Movie does not Exists");
        }
        Showtime showtime = showtimeOpt.get();

        //check if  seatNumber not more then configurated max seats in this theater
        Integer seatNumber = bookingEntryDto.getSeat_number();
        Theater bookingTheater = showtime.getTheater();
        if(seatNumber > maxSeatsByTheaterPerSubjectMap.get(bookingTheater.getValue())){
            throw new RuntimeException("Booking Seat number is more than the maximum number of seats in this theater");
        }

        //Ensure no seat is booked twice for the same showtime
        if(bookingRepository.isExistBookingForThisShowtimeAndSeatNumber(bookingEntryDto.getShowtimeId(), seatNumber)) {
            throw new RuntimeException("This seat number was booked for choosed Showtime");
        }

        //check if we can to book for this showtime ( if startTime did not pass )
        if(showtime.getStart_time().isBefore(LocalDateTime.now())){
            throw new RuntimeException("You try to book for unavailable showtime");
        }

        //get current authentication user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = (authentication != null) ? authentication.getName() : null;
        if(userName == null){
            throw new RuntimeException("Don't exist some authorization user");
        }
        Optional<User> userOpt = userRepository.findByUsername(userName);
        if (userOpt.isEmpty()) {
            throw new RuntimeException(String.format("Don't exist authorization user with %s Username", userName));
        }
        User user =userOpt.get();

        //save to DB
        Booking booking = Booking
                .builder()
                .userB(user)
                .movie(movie)
                .showtime(showtime)
                .seat_number(bookingEntryDto.getSeat_number())
                .price(bookingEntryDto.getPrice())
                .build();

        bookingRepository.save(booking);

        //Track booking details
        BookingDetails bookingDetails = BookingDetails
                .builder()
                .userName(userName)
                .movie(movie)
                .theater(showtime.getTheater())
                .start_time(showtime.getStart_time())
                .end_time(showtime.getEnd_time())
                .seat_number(bookingEntryDto.getSeat_number())
                .price(bookingEntryDto.getPrice())
                .build();

        logger.info("Booked successfully ticket for {}", bookingDetails);

        return "Booking Saved Successfully";
    }

    @Data
    @Builder
    public static class BookingDetails {
        private String userName;
        private Movie movie;
        private Theater theater;
        private LocalDateTime start_time;
        private LocalDateTime end_time;
        private Integer seat_number;
        private Integer price;
    }
}
