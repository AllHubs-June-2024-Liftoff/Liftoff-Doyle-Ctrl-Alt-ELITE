package org.launchcode.BingeBuddy.service;

import org.launchcode.BingeBuddy.data.UserEntityRepository;
import org.launchcode.BingeBuddy.model.JwtRequest;
import org.launchcode.BingeBuddy.model.JwtResponse;
import org.launchcode.BingeBuddy.model.User;
import org.launchcode.BingeBuddy.util.JwtTokenUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;
    private final UserEntityRepository userEntityRepository;

    public AuthService(AuthenticationManager authenticationManager, JwtTokenUtil jwtUtil, CustomUserDetailsService userDetailsService, UserEntityRepository userEntityRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.userEntityRepository = userEntityRepository;
    }

    public JwtResponse authenticate(JwtRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        User user = userEntityRepository.findByUsername(userDetails.getUsername());

        String token = jwtUtil.generateToken(user.getId().toString());      // ID as claim
       //String token = jwtUtil.generateToken(userDetails.getUsername());

        return new JwtResponse(token);
    }
}
