package com.videostore.management.service;

import com.videostore.management.helper.VideoStore;
import com.videostore.management.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class RentalService {

    private final VideoStore videoStore;

    @Autowired
    public RentalService(VideoStore videoStore) {
        this.videoStore = videoStore;
    }

    public RentFilmResponse rentFilms(String customerId, List<Long> filmIds, int daysRented) {
        Rental rental = videoStore.rentFilms(filmIds, customerId, daysRented);
        double rentalPrice = calculateRentalPrice(rental);
        return new RentFilmResponse(rental.getRentalId(), rentalPrice);
    }

    public ReturnFilmResponse returnFilms(String rentalId, List<Long> filmIds) {
        Rental rental = videoStore.returnFilms(rentalId, filmIds);
        double totalLateFee = calculateLateFees(rental);
        return new ReturnFilmResponse(totalLateFee);
    }

    private double calculateRentalPrice(Rental rental) {
        double totalPrice = 0.0;

        for (FilmRentalInfo filmInfo : rental.getFilms()) {
            Film film = videoStore.getFilmById(filmInfo.getId());
            long daysRented = filmInfo.getRentalDays();

            if (isNewRelease(film)) {
                totalPrice += daysRented * 40;
            } else if (isRegularFilm(film)) {
                totalPrice += 30 + (daysRented > 3 ? (daysRented - 3) * 30 : 0);
            } else if (isOldFilm(film)) {
                totalPrice += 30 + (daysRented > 5 ? (daysRented - 5) * 30 : 0);
            }
        }

        return totalPrice;
    }

    private double calculateLateFees(Rental rental) {
        double totalLateFee = 0.0;

        for (FilmRentalInfo filmInfo : rental.getReturnedFilms()) {
            if (filmInfo.getReturnDate() != null) {
                long daysLate = ChronoUnit.DAYS.between(filmInfo.getDueDate(), filmInfo.getReturnDate());

                if (daysLate > 0) {
                    Film film = videoStore.getFilmById(filmInfo.getId());
                    if (isNewRelease(film)) {
                        totalLateFee += daysLate * 40;
                    } else {
                        totalLateFee += daysLate * 30;
                    }
                }
            }
        }

        return totalLateFee;
    }

    private boolean isNewRelease(Film film) {
        return ChronoUnit.DAYS.between(film.getReleaseDate(), LocalDate.now()) <= 30;
    }

    private boolean isRegularFilm(Film film) {
        return ChronoUnit.DAYS.between(film.getReleaseDate(), LocalDate.now()) > 30 &&
                ChronoUnit.DAYS.between(film.getReleaseDate(), LocalDate.now()) <= 365;
    }

    private boolean isOldFilm(Film film) {
        return ChronoUnit.DAYS.between(film.getReleaseDate(), LocalDate.now()) > 365;
    }
}
