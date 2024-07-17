package com.example.coffeeshopmanagementsystem.security.service.impl.security;

import com.example.coffeeshopmanagementsystem.security.JwtTokenProvider;
import com.example.coffeeshopmanagementsystem.security.entity.AuthenticationRequest;
import com.example.coffeeshopmanagementsystem.security.entity.AuthenticationResponse;
import com.example.coffeeshopmanagementsystem.security.entity.RefreshToken;
import com.example.coffeeshopmanagementsystem.security.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;

    public AuthenticationResponse authenticate (AuthenticationRequest request)
    {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + request.getUsername()));
        var jwtToken = jwtTokenProvider.generateToken(user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken.getToken())
                .build();
    }


}
