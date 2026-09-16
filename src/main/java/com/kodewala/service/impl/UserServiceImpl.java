package com.kodewala.service.impl;

import com.kodewala.entity.UserEntity;
import com.kodewala.io.UserRequest;
import com.kodewala.io.UserResponse;
import com.kodewala.repository.UserRepository;
import com.kodewala.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserResponse registerUser(UserRequest request) {
        UserEntity newUser=convertToEntity(request);
        newUser=userRepository.save(newUser);
       return convertToResponse(newUser);
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
