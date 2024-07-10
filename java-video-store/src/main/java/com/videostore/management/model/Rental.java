package com.videostore.management.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rental {
    private String rentalId;
    private String customerId;
    private LocalDate rentalDate;
    private int rentalDays;
    private List<FilmRentalInfo> films = new ArrayList<>();
    private List<FilmRentalInfo> returnedFilms = new ArrayList<>();

    // Constructor for rental without films (used in RentalService)
    public Rental(String rentalId, String customerId, LocalDate rentalDate, int rentalDays) {
        this.rentalId = rentalId;
        this.customerId = customerId;
        this.rentalDate = rentalDate;
        this.rentalDays = rentalDays;
    }

    // Constructor for rental with films
    public Rental(String rentalId, String customerId, LocalDate rentalDate, int rentalDays, List<FilmRentalInfo> films) {
        this.rentalId = rentalId;
        this.customerId = customerId;
        this.rentalDate = rentalDate;
        this.rentalDays = rentalDays;
        this.films = films;
    }

    public void addFilm(Long filmId, String filmTitle, LocalDate dueDate, int rentalDays) {
        films.add(new FilmRentalInfo(filmId, filmTitle, dueDate, rentalDays));
    }

    public void returnFilm(Long filmId, LocalDate returnDate) {
        films.stream()
                .filter(film -> film.getId().equals(filmId))
                .findFirst()
                .ifPresent(film -> {
                    film.setReturnDate(returnDate);
                    returnedFilms.add(film);
                });
    }
}
