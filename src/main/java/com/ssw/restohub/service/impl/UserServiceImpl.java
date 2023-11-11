package com.ssw.restohub.service.impl;

import com.ssw.restohub.data.UserRole;
import com.ssw.restohub.repositories.UserRepository;
import com.ssw.restohub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    @Autowired
    UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserRole getUser(String email){
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("The email entered doesn't exit!"));
    }

    @Override
    public Optional<UserRole> userExistsCheck(String email) {
        return userRepository.findByEmail(email);
    }

}
