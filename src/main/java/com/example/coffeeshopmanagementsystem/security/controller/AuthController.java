package com.example.coffeeshopmanagementsystem.security.controller;

import com.example.coffeeshopmanagementsystem.exception.secruity.TokenRefreshException;
import com.example.coffeeshopmanagementsystem.security.JwtTokenProvider;
import com.example.coffeeshopmanagementsystem.security.entity.AuthenticationRequest;
import com.example.coffeeshopmanagementsystem.security.entity.AuthenticationResponse;
import com.example.coffeeshopmanagementsystem.security.entity.TokenRefreshRequest;
import com.example.coffeeshopmanagementsystem.security.entity.TokenRefreshResponse;
import com.example.coffeeshopmanagementsystem.security.service.impl.security.AuthenticationService;
import com.example.coffeeshopmanagementsystem.security.service.impl.security.CustomUserDetailsService;
import com.example.coffeeshopmanagementsystem.security.service.impl.security.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;
    private final RefreshTokenService refreshTokenService;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();
        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(refreshToken -> {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(refreshToken.getUser().getUsername());
                    String newJwtToken = jwtTokenProvider.generateToken(userDetails);
                    return ResponseEntity.ok(new TokenRefreshResponse(newJwtToken, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Refresh token is not in database!"));
    }

}
