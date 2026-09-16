package com.kodewala.service.impl;

import com.kodewala.entity.UserEntity;
import com.kodewala.io.UserRequest;
import com.kodewala.io.UserResponse;
import com.kodewala.repository.UserRepository;
import com.kodewala.service.AuthenticationFacade;
import com.kodewala.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationFacade authenticationFacade;

    @Override
    public UserResponse registerUser(UserRequest request) {
        UserEntity newUser=convertToEntity(request);
        newUser=userRepository.save(newUser);
       return convertToResponse(newUser);
    }

    @Override
    public String findByUserId() {
        String loggedInUserEmail=authenticationFacade.getAuthentication().getName();
        UserEntity loggedInUser= userRepository.findByEmail(loggedInUserEmail).orElseThrow(()->new UsernameNotFoundException("User not found"));
        return loggedInUser.getId();
    }

    private UserEntity convertToEntity(UserRequest request){
       return UserEntity.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .build();
    }
    private UserResponse convertToResponse(UserEntity registeredUser){
        return UserResponse.builder()
                .id(registeredUser.getId())
                .name(registeredUser.getName())
                .email(registeredUser.getEmail())
                .build();

    }
}
