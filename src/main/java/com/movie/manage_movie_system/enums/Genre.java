package com.movie.manage_movie_system.enums;


public enum Genre {
    DRAMA("Drama"),
    THRILLER("Thriller"),
    ACTION("Action"),
    ROMANTIC("Romantic"),
    COMEDY("Comedy"),
    HISTORICAL("Historical"),
    ANIMATION("Animation"),
    SPORTS("Sports"),
    SOCIAL("Social"),
    WAR("War");

    private final String genre;

    Genre(String genre) {
        this.genre = genre;
    }

    public String getValue() {
        return genre;
    }
}
