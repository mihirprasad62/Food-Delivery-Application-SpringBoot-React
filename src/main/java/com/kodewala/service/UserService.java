package com.kodewala.service;

import com.kodewala.io.UserRequest;
import com.kodewala.io.UserResponse;

public interface UserService {
    UserResponse registerUser(UserRequest request);

    String findByUserId();
}
