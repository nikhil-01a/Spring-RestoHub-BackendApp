package com.ssw.restohub.controllers;

import com.ssw.restohub.data.Reservation;
import com.ssw.restohub.data.Restaurant;
import com.ssw.restohub.data.UserRole;
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
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class RestaurantController {
    private RestaurantService restaurantService;
    private UserService userService;
    private ReservationService reservationService;
    private ReservationRepository reservationRepository;

    @Autowired
    public RestaurantController(UserService userService, RestaurantService restaurantService,
                                ReservationService reservationService, ReservationRepository reservationRepository) {
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

    @GetMapping("/userInfo")
    public ResponseEntity<UserRole> getUser(@RequestParam(value = "userId") String userId){
        return new ResponseEntity<>(userService.getUser(userId),HttpStatus.OK);
    }

    @GetMapping("/reservations/getReservedTimes")
    public ResponseEntity<List<UnavailableReservationTime>> getAllUnavailableReservations(@RequestParam(value = "restaurantId") Long restaurantId,
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
}
