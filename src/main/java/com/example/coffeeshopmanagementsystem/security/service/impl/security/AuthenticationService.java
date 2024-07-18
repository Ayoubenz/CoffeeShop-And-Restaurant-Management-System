package com.example.coffeeshopmanagementsystem.security.service.impl.security;

import com.example.coffeeshopmanagementsystem.security.JwtTokenProvider;
import com.example.coffeeshopmanagementsystem.security.entity.AuthenticationRequest;
import com.example.coffeeshopmanagementsystem.security.entity.AuthenticationResponse;
import com.example.coffeeshopmanagementsystem.security.entity.RefreshToken;
import com.example.coffeeshopmanagementsystem.security.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Value("${app.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;

    public AuthenticationResponse authenticate (AuthenticationRequest request)
    {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + request.getUsername()));
        var jwtToken = jwtTokenProvider.generateToken(user);

        // Check if a refresh token already exists
        Optional<RefreshToken> existingTokenOpt = refreshTokenService.findByUserId(user.getId());

        RefreshToken refreshToken;
        if (existingTokenOpt.isPresent()){
            refreshToken = existingTokenOpt.get();
            refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
            refreshToken = refreshTokenService.save(refreshToken);
        } else {
            // Create a new refresh token if not present
            refreshToken = refreshTokenService.createRefreshToken(user.getId());
        }

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken.getToken())
                .build();
    }


}
