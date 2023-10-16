package com.ssw.restohub.controllers;

import com.ssw.restohub.data.Restaurant;
import com.ssw.restohub.data.UserRole;
import com.ssw.restohub.service.RestaurantService;

import com.ssw.restohub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RestaurantController {
    private RestaurantService restaurantService;
    private UserService userService;

    @Autowired
    public RestaurantController(UserService userService, RestaurantService restaurantService) {
        this.userService = userService;
        this.restaurantService = restaurantService;
    }

    @CrossOrigin
    @GetMapping("/restaurants")
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        return new ResponseEntity<>(restaurantService.getAllRestaurants(), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/restaurants/search")
    public ResponseEntity<List<Restaurant>> getRestaurantsByZipCode(@RequestParam(value = "zipCode") String zipCode) {
        return new ResponseEntity<>(restaurantService.getRestaurantsByZipCode(zipCode), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/userInfo")
    public ResponseEntity<UserRole> getUser(@RequestParam(value = "userId") String userId){
        return new ResponseEntity<>(userService.getUser(userId),HttpStatus.OK);
    }


}
