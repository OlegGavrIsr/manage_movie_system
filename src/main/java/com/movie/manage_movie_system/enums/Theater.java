package com.movie.manage_movie_system.enums;

public enum Theater {
    CINEMACITY("CinemaCity"),
    HOTCINEMA("HotCinema"),
    GOODCINEMA("GoodCinema");

    private final String theater;

    Theater(String theater) {
        this.theater = theater;
    }

    public String getValue() {
        return theater;
    }

}
