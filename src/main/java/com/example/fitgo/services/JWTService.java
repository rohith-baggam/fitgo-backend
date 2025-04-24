package com.example.fitgo.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.fitgo.model.Users;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * JWTService is responsible for creating, validating, and extracting claims
 * from JWT tokens.
 * It uses HMAC-SHA256 to sign the JWT and validates tokens with the same key.
 */
@Service
public class JWTService {
    @Value("${jwt_secret}")
    private String secretKey; // The secret key used to sign the JWT
                              // tokens

    /**
     * Constructor that generates a new secret key for signing JWT tokens.
     * The key is generated using HMACSHA256 algorithm and is encoded to Base64.
     */
    public JWTService() {
    }

    /**
     * Generates a JWT token for the provided user.
     * 
     * @param users The user for whom the token is being generated.
     * @return The generated JWT token as a String.
     */
    public String generateToken(Users users) {

        Map<String, Object> claims = new HashMap<>(); // Claims to include in the token
        return Jwts.builder()
                .claims() // Claims (currently empty) that will be added to the token
                .add(claims)
                .subject(users.getUsername()) // Set the subject to the username of the user
                .issuedAt(new Date(System.currentTimeMillis())) // Set the issue time of the token
                .expiration(new Date(System.currentTimeMillis() * 60 * 60 * 30)) // Set expiration time (30 hours from
                                                                                 // now)
                .and()
                .signWith(getKey()) // Sign the token with the secret key
                .compact(); // Generate and return the compact JWT token
    }

    /**
     * Retrieves the SecretKey from the base64-encoded secret key string.
     *
     * @return The SecretKey used for signing and validating JWTs.
     */
    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey); // Decode the base64 secret key
        return Keys.hmacShaKeyFor(keyBytes); // Create and return the HMAC key
    }

    /**
     * Extracts the username (subject) from the JWT token.
     * 
     * @param token The JWT token to extract the username from.
     * @return The username (subject) extracted from the token.
     */
    public String extractUserName(String token) {
        // Extract and return the username (subject) from the JWT token
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts a specific claim from the JWT token using the provided claim
     * resolver.
     * 
     * @param token         The JWT token to extract the claim from.
     * @param claimResolver The function to resolve the desired claim.
     * @param <T>           The type of the claim.
     * @return The value of the requested claim.
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token); // Extract all claims from the token
        return claimResolver.apply(claims); // Apply the claim resolver to extract the claim
    }

    /**
     * Extracts all claims from the JWT token.
     * 
     * @param token The JWT token to extract claims from.
     * @return The claims extracted from the token.
     */
    private Claims extractAllClaims(String token) {
        // Parse the token and extract claims using the provided secret key

        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Validates the JWT token by checking if the username matches and the token is
     * not expired.
     * 
     * @param token       The JWT token to validate.
     * @param userDetails The user details to compare the username with.
     * @return True if the token is valid; otherwise, false.
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUserName(token); // Extract the username from the token
        // Return true if the username matches and the token is not expired
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Checks if the token has expired.
     * 
     * @param token The JWT token to check for expiration.
     * @return True if the token is expired; otherwise, false.
     */
    private boolean isTokenExpired(String token) {
        // Extract the expiration date from the token and check if it's before the
        // current date
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from the JWT token.
     * 
     * @param token The JWT token to extract the expiration date from.
     * @return The expiration date of the token.
     */
    private Date extractExpiration(String token) {
        // Extract and return the expiration date from the token
        return extractClaim(token, Claims::getExpiration);
    }
}
