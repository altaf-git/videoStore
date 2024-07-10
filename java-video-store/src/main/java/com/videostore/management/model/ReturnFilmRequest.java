package com.videostore.management.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnFilmRequest {
    private String rentalId;
    private List<Long> filmIds;
}
