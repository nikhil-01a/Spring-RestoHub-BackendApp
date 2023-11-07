package com.ssw.restohub.controllers;

import com.ssw.restohub.data.Restaurant;
import com.ssw.restohub.data.UserRole;
import com.ssw.restohub.enums.AppRole;
import com.ssw.restohub.pojo.JwtRequest;
import com.ssw.restohub.pojo.JwtResponse;
import com.ssw.restohub.security.JwtHelper;
import com.ssw.restohub.service.AuthService;
import com.ssw.restohub.service.RestaurantService;
import com.ssw.restohub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
public class AuthController {
    private UserDetailsService userDetailsService; // To fetch user details
    private JwtHelper jwtHelper; // To generate token
    private UserService userService;

    private RestaurantService restaurantService;

    private AuthService authService; // kept our doAuthenticate() in this

    @Autowired
    private AuthController(UserDetailsService userDetailsService, JwtHelper jwtHelper, AuthService authService,UserService userService, RestaurantService restaurantService){
        this.userDetailsService = userDetailsService;
        this.jwtHelper = jwtHelper;
        this.authService = authService;
        this.userService = userService;
        this.restaurantService = restaurantService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestParam("email") String email, @RequestParam("password") String password){
        JwtRequest jwtRequest = new JwtRequest(email,password);
        authService.doAuthenticate(jwtRequest.getEmail(),jwtRequest.getPassword()); // To authenticate incoming username and password
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getEmail());
        String token = jwtHelper.generateToken(userDetails);
        UserRole currentUserRole = userService.getUser(jwtRequest.getEmail());
        JwtResponse jwtResponse = JwtResponse.builder()
                .jwtToken(token)
                .userRole(currentUserRole)
                .restaurant(restaurantExists(currentUserRole))
                .build();

        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }

    private Optional<Restaurant> restaurantExists (UserRole currentuserRole){
        if (currentuserRole.getAppRole().equals(AppRole.RESTAURANT_MANAGER) | currentuserRole.getAppRole().equals(AppRole.RESTAURANT_STAFF) | currentuserRole.getAppRole().equals(AppRole.RESTAURANT_WAITER) ){
            return restaurantService.getRestaurantByManager(currentuserRole.getEmail());
        }
        return null;
    }


}
