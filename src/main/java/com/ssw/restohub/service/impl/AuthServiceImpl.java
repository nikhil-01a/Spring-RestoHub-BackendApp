package com.ssw.restohub.service.impl;

import com.ssw.restohub.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager; // To authenticate

    @Autowired
    private AuthServiceImpl(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void doAuthenticate(String email, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email,password); // Creating username & password authentication token
        try { authenticationManager.authenticate(authenticationToken); // Using 'AuthenticationManager' which uses 'DaoAuthenticationProvider' which uses 'UserDetailsService' which uses 'UserDetails' which uses 'UserRole' entity
        } catch (BadCredentialsException e){
            throw new RuntimeException("Invalid Username or Password");
        }
    }
}
