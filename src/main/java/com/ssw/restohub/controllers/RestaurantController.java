package com.ssw.restohub.controllers;

import com.ssw.restohub.data.Reservation;
import com.ssw.restohub.data.Restaurant;
import com.ssw.restohub.data.UserRole;
import com.ssw.restohub.service.ReservationService;
import com.ssw.restohub.service.RestaurantService;

import com.ssw.restohub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Autowired
    public RestaurantController(UserService userService, RestaurantService restaurantService, ReservationService reservationService) {
        this.userService = userService;
        this.restaurantService = restaurantService;
        this.reservationService = reservationService;
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
    public ResponseEntity<List<Reservation>> getAllUnavailableReservations(@RequestParam(value = "restaurantId") Long restaurantId,
                                                                           @RequestParam(value = "partySize") Integer partySize) {
        return new ResponseEntity<>(reservationService.getUnavailableReservations(restaurantId, partySize), HttpStatus.OK);
    }


}
