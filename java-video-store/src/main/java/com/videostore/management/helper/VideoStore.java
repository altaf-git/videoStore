package com.videostore.management.helper;

import com.videostore.management.model.Film;
import com.videostore.management.model.Rental;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class VideoStore {
    private final Map<Long, Film> films = new HashMap<>();
    private final Map<String, Rental> rentals = new HashMap<>();
    private final Map<String, Long> nextRentalId = new HashMap<>();
    private long nextFilmId = 1L;

    public Film addFilm(Film film) {
        film.setId(nextFilmId++);
        films.put(film.getId(), film);
        return film;
    }

    public Rental rentFilms(List<Long> filmIds, String customerId, int daysRented) {
        String rentalId = generateRentalId(customerId);
        Rental rental = new Rental(rentalId, customerId, LocalDate.now(), daysRented);

        for (Long filmId : filmIds) {
            Film film = films.get(filmId);
            if (film != null) {
                rental.addFilm(filmId, film.getTitle(), LocalDate.now().plusDays(daysRented), daysRented);
            } else {
                throw new IllegalArgumentException("Film with ID " + filmId + " not found.");
            }
        }

        rentals.put(rentalId, rental);
        return rental;
    }

    public Rental returnFilms(String rentalId, List<Long> filmIds) {
        Rental rental = rentals.get(rentalId);
        if (rental == null) {
            throw new IllegalArgumentException("Rental with ID " + rentalId + " not found.");
        }

        for (Long filmId : filmIds) {
            rental.returnFilm(filmId, LocalDate.now());
        }

        return rental;
    }

    public Film getFilmById(Long id) {
        return films.get(id);
    }

    public void addRental(Rental rental) {
        rentals.put(rental.getRentalId(), rental);
    }

    public Rental getRental(String rentalId) {
        return rentals.get(rentalId);
    }

    public void updateRental(Rental rental) {
        rentals.put(rental.getRentalId(), rental);
    }

    public String generateRentalId(String customerId) {
        long id = nextRentalId.getOrDefault(customerId, 0L) + 1;
        nextRentalId.put(customerId, id);
        return customerId + "-" + id;
    }
}
