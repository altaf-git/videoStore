package com.videostore.management.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RentFilmResponse {
    private String rentalId;
    private double rentalPrice;
}
