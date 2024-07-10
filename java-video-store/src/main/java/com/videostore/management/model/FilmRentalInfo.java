package com.videostore.management.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilmRentalInfo {
    private Long id;
    private String title;
    private LocalDate dueDate;
    private int rentalDays;
    private LocalDate returnDate;

    // Constructor without returnDate
    public FilmRentalInfo(Long id, String title, LocalDate dueDate, int rentalDays) {
        this.id = id;
        this.title = title;
        this.dueDate = dueDate;
        this.rentalDays = rentalDays;
    }
}
