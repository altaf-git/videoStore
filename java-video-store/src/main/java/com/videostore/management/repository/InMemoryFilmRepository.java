package com.videostore.management.repository;

import com.videostore.management.model.Film;
import com.videostore.management.model.Rental;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryFilmRepository {
    private final List<Film> films = new ArrayList<>();
    private final List<Rental> rentals = new ArrayList<>();

    public List<Film> findAll() {
        return films;
    }

    public void addFilm(Film film) {
        films.add(film);
    }

    public List<Rental> findAllRentals() {
        return rentals;
    }

    public void addRental(Rental rental) {
        rentals.add(rental);
    }
}
