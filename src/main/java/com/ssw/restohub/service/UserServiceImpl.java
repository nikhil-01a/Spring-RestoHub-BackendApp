package com.ssw.restohub.service;

import com.ssw.restohub.data.UserRole;
import com.ssw.restohub.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    @Autowired
    UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserRole getUser(String username){
        return userRepository.findUserRoleByUserId(username);
    }

}
