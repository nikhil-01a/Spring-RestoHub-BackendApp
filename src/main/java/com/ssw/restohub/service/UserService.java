package com.ssw.restohub.service;

import com.ssw.restohub.data.UserRole;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {

     UserRole getUser(String email);

     Optional<UserRole> userExistsCheck(String email);

}
