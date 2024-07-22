package com.example.coffeeshopmanagementsystem.security.service.impl.security;

import com.example.coffeeshopmanagementsystem.security.JwtTokenProvider;
import com.example.coffeeshopmanagementsystem.security.exception.TokenRefreshException;
import com.example.coffeeshopmanagementsystem.security.entity.RefreshToken;
import com.example.coffeeshopmanagementsystem.security.repository.RefreshTokenRepository;
import com.example.coffeeshopmanagementsystem.security.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    @Value("${app.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public Optional<RefreshToken> findByUserId(Long id){
        return refreshTokenRepository.findByUserId(id);
    }
    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User doesn't exist with the given id "+ userId)));
        String refreshTokenJwt = jwtTokenProvider.generateRefreshToken(userId, refreshTokenDurationMs);

        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(refreshTokenJwt);

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new sign in request");
        }

        return token;
    }
    public RefreshToken save(RefreshToken token){
        return refreshTokenRepository.save(token);
    }
    @Transactional
    public int deleteByUserId(Long userId) {
        return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
    }
}
