package com.kodewala.service.impl;

import com.kodewala.entity.UserEntity;
import com.kodewala.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service

public class AppUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
      UserEntity user= userRepository.findByEmail(email)
               .orElseThrow(()->new UsernameNotFoundException("User not found"));

      return new User(user.getEmail(),user.getPassword(), Collections.emptyList());
    }
}
