package com.movie.manage_movie_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class ManageMovieSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManageMovieSystemApplication.class, args);
	}

}
