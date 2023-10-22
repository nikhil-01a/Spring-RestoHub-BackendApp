package com.ssw.restohub.controllers;

import com.ssw.restohub.data.UserRole;
import com.ssw.restohub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;

    @Autowired
    private UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/userInfo")
    public ResponseEntity<UserRole> getUser(@RequestParam(value = "userId") String userId){
        return new ResponseEntity<>(userService.getUser(userId), HttpStatus.OK);
    }

    @GetMapping("/current-user")
    private String getLoggedUser(Principal principal){
        return principal.getName();
    }
}
