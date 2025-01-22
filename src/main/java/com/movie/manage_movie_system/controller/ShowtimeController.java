package com.movie.manage_movie_system.controller;

import com.movie.manage_movie_system.dto.request.ShowEntryDto;
import com.movie.manage_movie_system.model.Showtime;
import com.movie.manage_movie_system.service.ShowtimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/showtime")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class ShowtimeController {

    @Autowired
    private ShowtimeService showtimeService;

    @GetMapping("/{id}")
    public ResponseEntity<Showtime> getShowtimeById(@PathVariable Long id) {
        Showtime showtime = showtimeService.getShowtimeById(id);
        if (showtime != null) {
            return new ResponseEntity<>(showtime, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<String> saveShowtime(@RequestBody ShowEntryDto showEntryDto) {
        try {
            String result = showtimeService.saveShowtime(showEntryDto);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{showtimeId}")
    public ResponseEntity<Showtime> updateShowtime(@PathVariable Long showtimeId, @RequestBody ShowEntryDto showEntryDto) {
        Showtime result = showtimeService.updateShowtime(showtimeId, showEntryDto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{showtimeId}")
    public ResponseEntity<Void> deleteShowtime(@PathVariable Long showtimeId) {
        showtimeService.deleteShowtime(showtimeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/byMovie/{id}")
    public ResponseEntity<List<Showtime>> getShowtimesByMovieTitle(@PathVariable String title) {
        List<Showtime> showtimes = showtimeService.getShowtimesByMovieTitle(title);
        if (showtimes != null && !showtimes.isEmpty()) {
            return new ResponseEntity<>(showtimes, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/byTheater/{theaterName}")
    public ResponseEntity<List<Showtime>> getShowtimesByTheater(@PathVariable String theaterName) {
        List<Showtime> showtimes = showtimeService.getShowtimesByTheater(theaterName);
        if (showtimes != null && !showtimes.isEmpty()) {
            return new ResponseEntity<>(showtimes, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
