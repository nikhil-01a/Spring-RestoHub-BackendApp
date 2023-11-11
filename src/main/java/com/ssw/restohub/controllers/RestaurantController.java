package com.ssw.restohub.controllers;

import com.ssw.restohub.data.Reservation;
import com.ssw.restohub.data.Restaurant;
import com.ssw.restohub.pojo.ReservationBean;
import com.ssw.restohub.projection.UnavailableReservationTime;
import com.ssw.restohub.repositories.ReservationRepository;
import com.ssw.restohub.service.ReservationService;
import com.ssw.restohub.service.RestaurantService;

import com.ssw.restohub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class RestaurantController {
    private RestaurantService restaurantService;
    private UserService userService;
    private ReservationService reservationService;
    private ReservationRepository reservationRepository;

    @Autowired
    public RestaurantController(UserService userService, RestaurantService restaurantService, ReservationService reservationService) {
        this.userService = userService;
        this.restaurantService = restaurantService;
        this.reservationService = reservationService;
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/restaurants")
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        return new ResponseEntity<>(restaurantService.getAllRestaurants(), HttpStatus.OK);
    }

    @GetMapping("/restaurants/search")
    public ResponseEntity<List<Restaurant>> getRestaurantsByZipCode(@RequestParam(value = "zipCode") String zipCode) {
        return new ResponseEntity<>(restaurantService.getRestaurantsByZipCode(zipCode), HttpStatus.OK);
    }

    @GetMapping("/reservations/getReservedTimes")
    public ResponseEntity<List<UnavailableReservationTime>> getAllUnavailableReservations(
            @RequestParam(value = "restaurantId") Long restaurantId,
            @RequestParam(value = "partySize") Integer partySize)
            throws Exception {
        return new ResponseEntity<>(reservationService.getUnavailableReservations(restaurantId, partySize), HttpStatus.OK);
    }

    @PostMapping(value = "/reservations/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationBean reservationBean)
            throws Exception {
        return new ResponseEntity<>(reservationService.saveReservation(reservationBean), HttpStatus.OK);
    }

    @GetMapping("/reservations/search")
    public ResponseEntity<List<Reservation>> getAllReservationsForRestaurantAndTimeFrame(
            @RequestParam(value = "restaurantId") Long restaurantId,
            @RequestParam(value = "startDate") String startDate,
            @RequestParam(value = "endDate") String endDate) throws Exception {
        return new ResponseEntity<>(
                reservationService.getReservationForRestaurantAndTimeFrame(restaurantId, startDate, endDate),
                HttpStatus.OK);
    }

    @PutMapping(value = "/reservations/checkIn")
    public ResponseEntity<Reservation> updateReservationCheckIn(
            @RequestParam(name = "reservationId") Long reservationId) throws Exception {
        return new ResponseEntity<>(reservationService.customerReservationCheckIn(reservationId), HttpStatus.OK);
    }
}
