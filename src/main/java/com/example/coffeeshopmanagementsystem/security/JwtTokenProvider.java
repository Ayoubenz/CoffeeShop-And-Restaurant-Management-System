package com.example.coffeeshopmanagementsystem.security;

import com.example.coffeeshopmanagementsystem.security.exception.TokenExpiredException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class JwtTokenProvider {


    @Value("${jwt.key}")
    private String key;

    @Value("${app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String extractUsername (String jwtToken)
    {   try {
        return extractClaim(jwtToken, Claims::getSubject);
        }catch (ExpiredJwtException e) {
        // Handle token expiration, e.g., throw a custom exception or refresh the token
        throw new TokenExpiredException("Token has expired. Please login again.");
    }

    }
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }

    public String generateToken(Map<String,Object> claims, UserDetails userDetails){
        return Jwts
                .builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000 * 60 * 24 *30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .setSubject(userDetails.getUsername())
                .compact();
    }

    public String generateRefreshToken(Long userId, long expirationTime) {
        return Jwts
                .builder()
                .setSubject(userId.toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, getSignKey())
                .compact();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(key).build().parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    public boolean isTokenValid(String token,UserDetails userDetails){
        String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpirationDate(token).before(new Date());
    }

    private Date extractExpirationDate(String token) {
        return extractClaim(token,Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolve) {
        return resolve.apply(extractAllClaims(token));
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
