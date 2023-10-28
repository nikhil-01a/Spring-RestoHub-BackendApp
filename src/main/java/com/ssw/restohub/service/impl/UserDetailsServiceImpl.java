package com.ssw.restohub.service.impl;

import com.ssw.restohub.data.UserRole;
import com.ssw.restohub.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// Its interface is in spring security core.

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserRole user = userRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("The given email doesn't exist"));
        return user; // Since we made UserRole implement UserDetails in our Entity class, we can return UserRole form UserDetails return type here.
    }
}
