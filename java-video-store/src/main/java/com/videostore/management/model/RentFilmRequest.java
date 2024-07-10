package com.videostore.management.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentFilmRequest {
    private List<Long> filmIds;
    private String customerId;
    private int daysRented;
}
