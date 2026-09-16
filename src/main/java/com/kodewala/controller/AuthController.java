package com.kodewala.controller;

import com.kodewala.io.AuthenticationRequest;
import com.kodewala.io.AuthenticationResponse;
import com.kodewala.service.impl.AppUserDetailsService;
import com.kodewala.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    AppUserDetailsService appUserDetailsService;
    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest authenticationRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),authenticationRequest.getPassword()));
       final UserDetails userDetails= appUserDetailsService.loadUserByUsername(authenticationRequest.getEmail());
       final String jwtToken=jwtUtil.generateToken(userDetails);
       return new AuthenticationResponse(authenticationRequest.getEmail(),jwtToken);
    }
}
