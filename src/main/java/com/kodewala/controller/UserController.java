package com.kodewala.controller;

import com.kodewala.io.UserRequest;
import com.kodewala.io.UserResponse;
import com.kodewala.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")

public class UserController {
    @Autowired
    UserService userService;


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@RequestBody UserRequest request){
       return userService.registerUser(request);

    }
}
