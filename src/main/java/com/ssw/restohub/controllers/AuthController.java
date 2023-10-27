package com.ssw.restohub.controllers;

import com.ssw.restohub.data.UserRole;
import com.ssw.restohub.pojo.JwtRequest;
import com.ssw.restohub.pojo.JwtResponse;
import com.ssw.restohub.security.JwtHelper;
import com.ssw.restohub.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class AuthController {
    private UserDetailsService userDetailsService; // To fetch user details
    private JwtHelper jwtHelper; // To generate token

    private AuthService authService; // kept our doAuthenticate() in this

    @Autowired
    private AuthController(UserDetailsService userDetailsService, JwtHelper jwtHelper, AuthService authService){
        this.userDetailsService = userDetailsService;
        this.jwtHelper = jwtHelper;
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestParam("email") String email, @RequestParam("password") String password){
        JwtRequest jwtRequest = new JwtRequest(email,password);
        authService.doAuthenticate(jwtRequest.getEmail(),jwtRequest.getPassword()); // To authenticate incoming username and password
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getEmail());
        String token = jwtHelper.generateToken(userDetails);
        UserRole currentUserRole = new UserRole();
        JwtResponse jwtResponse = JwtResponse.builder()
                .jwtToken(token)
                .userRole(currentUserRole)
                .build();
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }


}
