package com.videostore.management;

import com.videostore.management.helper.VideoStore;
import com.videostore.management.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class VideoStoreApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private VideoStore videoStore;

    @BeforeEach
    void setUp() {
        videoStore.addFilm(new Film(null, "New Release 1", LocalDate.now().minusDays(10)));
        videoStore.addFilm(new Film(null, "Regular Film 1", LocalDate.now().minusDays(100)));
        videoStore.addFilm(new Film(null, "Old Film 1", LocalDate.now().minusDays(400)));
    }

    @Test
    void testRentFilms() {
        String url = "http://localhost:" + port + "/rentals/rent";
        RentFilmRequest request = new RentFilmRequest(Arrays.asList(1L, 2L, 3L), "customer1", 5);
        HttpEntity<RentFilmRequest> entity = new HttpEntity<>(request);
        ResponseEntity<RentFilmResponse> response = restTemplate.postForEntity(url, entity, RentFilmResponse.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody().getRentalId()).isNotNull();
        assertThat(response.getBody().getRentalPrice()).isGreaterThan(0);
    }

    @Test
    void testReturnFilms() {
        // First rent some films
        String rentUrl = "http://localhost:" + port + "/rentals/rent";
        RentFilmRequest rentRequest = new RentFilmRequest(Arrays.asList(1L, 2L, 3L), "customer1", 5);
        HttpEntity<RentFilmRequest> rentEntity = new HttpEntity<>(rentRequest);
        ResponseEntity<RentFilmResponse> rentResponse = restTemplate.postForEntity(rentUrl, rentEntity, RentFilmResponse.class);
        // Then return them
        String rentalId = rentResponse.getBody().getRentalId();
        String returnUrl = "http://localhost:" + port + "/rentals/return";
        ReturnFilmRequest returnRequest = new ReturnFilmRequest(rentalId, Arrays.asList(1L, 2L, 3L));
        HttpEntity<ReturnFilmRequest> returnEntity = new HttpEntity<>(returnRequest);
        ResponseEntity<ReturnFilmResponse> returnResponse = restTemplate.postForEntity(returnUrl, returnEntity, ReturnFilmResponse.class);
        assertThat(returnResponse.getStatusCodeValue()).isEqualTo(200);
        assertThat(returnResponse.getBody().getTotalLateFee()).isNotNull();
    }
}
