package com.movie.manage_movie_system.service;

import com.movie.manage_movie_system.model.Movie;
import com.movie.manage_movie_system.repository.MovieRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public String saveMovie(Movie movie) {
        if (movieRepository.findByTitle(movie.getTitle()).isPresent()) {
            throw new RuntimeException("Movie with this title Already Exists");
        }

        movieRepository.save(movie);
        return "Movie Saved Successfully";
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(Long movieId) {
        Optional<Movie> movieOpt = movieRepository.findById(movieId);
        return movieOpt.orElse(null);
    }

    public Movie updateMovie(Long movieId, Movie movie) {
        Optional<Movie> movieOpt = movieRepository.findById(movieId);

        if(movieOpt.isEmpty())
            return null;

        Movie movieOrig = movieOpt.get();

        // Update the existing entity with the new values
        movieOrig.setDuration(movie.getDuration());
        movieOrig.setRating(movie.getDuration());
        movieOrig.setGenre(movie.getGenre());
        movieOrig.setTitle(movie.getTitle());
        movieOrig.setRelease_year(movie.getRelease_year());

        // Persist the changes to the database
        entityManager.merge(movieOrig);

        return movieOrig;
    }

    public void deleteMovie(Long movieId) {
        Optional<Movie> movieOpt = movieRepository.findById(movieId);
        movieOpt.ifPresent(movie -> movieRepository.delete(movie));
    }
}
