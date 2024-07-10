package com.videostore.management.controller;

import com.videostore.management.model.RentFilmRequest;
import com.videostore.management.model.RentFilmResponse;
import com.videostore.management.model.ReturnFilmRequest;
import com.videostore.management.model.ReturnFilmResponse;
import com.videostore.management.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rentals")
public class RentalController {

    private final RentalService rentalService;

    @Autowired
    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping("/rent")
    public ResponseEntity<RentFilmResponse> rentFilms(@RequestBody RentFilmRequest request) {
        RentFilmResponse response = rentalService.rentFilms(request.getCustomerId(), request.getFilmIds(), request.getDaysRented());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/return")
    public ResponseEntity<ReturnFilmResponse> returnFilms(@RequestBody ReturnFilmRequest request) {
        ReturnFilmResponse response = rentalService.returnFilms(request.getRentalId(), request.getFilmIds());
        return ResponseEntity.ok(response);
    }
}
