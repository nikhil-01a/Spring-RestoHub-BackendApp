package com.ssw.restohub.controllers;

import com.ssw.restohub.data.Restaurant;
import com.ssw.restohub.data.UserRole;
import com.ssw.restohub.repositories.RestaurantRepository;
import com.ssw.restohub.repositories.UserRepository;
import com.ssw.restohub.service.AuthService;
import com.ssw.restohub.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@RestController
@CrossOrigin
public class UserController {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private UserDetailsService userDetailsService; // To fetch user details

    private AuthService authService;

    private RestaurantService restaurantService;
    private RestaurantRepository restaurantRepository;

    @Autowired
    public UserController(UserRepository userRepository,PasswordEncoder passwordEncoder,AuthService authService,UserDetailsService userDetailsService,RestaurantService restaurantService,RestaurantRepository restaurantRepository){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authService = authService;
        this.userDetailsService = userDetailsService;
        this.restaurantService=restaurantService;
        this.restaurantRepository =restaurantRepository;
    }

    @PostMapping("/api/user/save-user")
    public ResponseEntity<UserRole> saveUser(@RequestBody UserRole userRole,@RequestParam(value = "restaurantId", required = false) Long restaurantId){
        userRole.setPassword(passwordEncoder.encode(userRole.getPassword()));
        userRepository.save(userRole);
        Optional<Restaurant> assignedRestaurant = restaurantService.getRestaurantById(restaurantId);
        assignedRestaurant.get().setManagerEmail(userRole.getEmail());
        restaurantRepository.save(assignedRestaurant.get());
        return new ResponseEntity<>(userRole, HttpStatus.OK);
    }

    // JSON TO QUICKLY STORE SOMEONE, Since we don't have a UI yet
    //    {
    //        "email": "nikhilkumar@restohub.com",
    //        "password": "restohub@123",
    //        "userAccess": "DATA_ACCESS",
    //        "appRole": "RESTOHUB_OWNER"
    //    }

    @PutMapping("/api/user/update-password")
    public String updatePassword(@RequestParam(value = "email") String email, @RequestParam(value = "currentPassword") String currentPassword, @RequestParam(value = "newPassword") String newPassword){
        try {
            authService.doAuthenticate(email,currentPassword);
        } catch (Exception e) {
            throw new RuntimeException("Couldn't verify user");
        }
        Optional<UserRole> dbUser = userRepository.findByEmail(email);
        if (dbUser.isPresent()){
            UserRole oldUser = dbUser.get();
            oldUser.setPassword(passwordEncoder.encode(newPassword));
            oldUser.setUpdateTime(new Date());
            userRepository.save(oldUser);
        } else {
            return "Failed to change the password!";
        }
        return "Password Changed Successfully";
    }
}
