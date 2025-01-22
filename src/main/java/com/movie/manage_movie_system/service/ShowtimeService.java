package com.movie.manage_movie_system.service;

import com.movie.manage_movie_system.dto.request.ShowEntryDto;
import com.movie.manage_movie_system.enums.Theater;
import com.movie.manage_movie_system.model.Movie;
import com.movie.manage_movie_system.model.Showtime;
import com.movie.manage_movie_system.repository.MovieRepository;
import com.movie.manage_movie_system.repository.ShowtimeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class ShowtimeService {

    @Autowired
    private ShowtimeRepository showtimeRepository;

    @Autowired
    private MovieRepository movieRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public String saveShowtime(ShowEntryDto showEntryDto) {

        //get Movie by movieId
        Optional<Movie> movieOpt = movieRepository.findById(showEntryDto.getMovieId());
        if (movieOpt.isEmpty()) {
            throw new RuntimeException("Movie does not Exists");
        }
        Movie movie = movieOpt.get();

        //get all showtimes for request theater and check if no overlapping showtime for the same theater.
        for (Showtime showtime : showtimeRepository.getAllShowsOfTheater(showEntryDto.getTheater())) {
            boolean bStartTimeBetween = !showEntryDto.getStart_time().isAfter(showtime.getEnd_time());
            boolean bEndTimeBetween = !showEntryDto.getEnd_time().isBefore(showtime.getStart_time());
            if(bStartTimeBetween && bEndTimeBetween)
                throw new RuntimeException("This Showtime is overlapping another showtime for the same theater");
        }

        Showtime showtime = Showtime
                .builder()
                .movie(movie)
                .theater(showEntryDto.getTheater())
                .start_time(showEntryDto.getStart_time())
                .end_time(showEntryDto.getEnd_time())
                .build();

        showtimeRepository.save(showtime);
        return "Showtime Saved Successfully";
    }

    public Showtime updateShowtime(Long showtimeId, ShowEntryDto showEntryDto) {

        //Get existing showtime from DB
        Optional<Showtime> showtimeOpt = showtimeRepository.findById(showtimeId);
        if(showtimeOpt.isEmpty())
            return null;
        Showtime showtimeOrig = showtimeOpt.get();

        //get all showtimes for request theater and check if no overlapping showtime for the same theater.
        for (Showtime showtime : showtimeRepository.getAllShowsOfTheater(showEntryDto.getTheater())) {
            boolean bStartTimeBetween = !showEntryDto.getStart_time().isAfter(showtime.getEnd_time());
            boolean bEndTimeBetween = !showEntryDto.getEnd_time().isBefore(showtime.getStart_time());
            if(bStartTimeBetween && bEndTimeBetween && !Objects.equals(showtime.getId(), showtimeOrig.getId()))
                return null;
        }

        //get Movie from movieId
        Optional<Movie> movieOpt = movieRepository.findById(showEntryDto.getMovieId());
        if (movieOpt.isEmpty()) {
            throw new RuntimeException("Movie does not Exists");
        }
        Movie movie = movieOpt.get();

        // Update the existing entity with the new values
        showtimeOrig.setMovie(movie);
        showtimeOrig.setTheater(showEntryDto.getTheater());
        showtimeOrig.setEnd_time(showEntryDto.getEnd_time());
        showtimeOrig.setStart_time(showEntryDto.getStart_time());

        // Persist the changes to the database
        entityManager.merge(showtimeOrig);

        return showtimeOrig;
    }

    public Showtime getShowtimeById(Long showtimeId) {
        Optional<Showtime> showtimeOpt = showtimeRepository.findById(showtimeId);
        return showtimeOpt.orElse(null);
    }

    public void deleteShowtime(Long showtimeId) {
        Optional<Movie> showtimeOpt = movieRepository.findById(showtimeId);
        showtimeOpt.ifPresent(showtime -> movieRepository.delete(showtime));
    }

    public List<Showtime> getShowtimesByMovieTitle(String title) {

        //get Movie from movieId
        Optional<Movie> movieOpt = movieRepository.findByTitle(title);
        if (movieOpt.isEmpty()) {
            return null;
        }
        Movie movie = movieOpt.get();


        return showtimeRepository.getAllShowsOfMovie(movie.getId());
    }

    public List<Showtime> getShowtimesByTheater(String theaterName) {
        return showtimeRepository.getAllShowsOfTheater(Theater.valueOf(theaterName));
    }
}
