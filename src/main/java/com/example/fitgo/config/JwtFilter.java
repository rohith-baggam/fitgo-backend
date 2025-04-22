package com.example.fitgo.config;

import com.example.fitgo.services.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

/**
 * JwtFilter is a filter that intercepts HTTP requests and checks for the
 * presence of a valid JWT token
 * in the "Authorization" header. If the token is valid, it sets the
 * authentication context for the user.
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * This method is invoked for every request to check if a JWT token is present
     * in the "Authorization" header.
     * If a valid JWT token is found, it authenticates the user and sets the
     * authentication context in the
     * SecurityContextHolder.
     *
     * @param request     The HTTP request being processed.
     * @param response    The HTTP response to be sent back.
     * @param filterChain The filter chain to pass the request and response to the
     *                    next filter or handler.
     * @throws ServletException If an error occurs during the filter execution.
     * @throws IOException      If an error occurs while reading or writing data
     *                          during the filter execution.
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // Extract the "Authorization" header from the request
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        // If the header is missing or does not start with "Bearer ", pass the request
        // along the filter chain
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract the JWT token from the "Authorization" header (remove the "Bearer "
        // prefix)
        jwt = authHeader.substring(7); // remove "Bearer "

        try {
            // Extract the username from the JWT token
            username = jwtService.extractUserName(jwt);

            // Check if the username is found and if the user is not already authenticated
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Load the user details using the username
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // Validate the JWT token and set the authentication in the SecurityContext if
                // valid
                if (jwtService.validateToken(jwt, userDetails)) {
                    // Create an authentication token with the user's authorities
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
                            null, userDetails.getAuthorities());

                    // Set the authentication token in the security context, marking the user as
                    // authenticated
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            // Continue the filter chain to process the request
            filterChain.doFilter(request, response);

        } catch (Exception ex) {
            // If JWT validation fails, send a 401 Unauthorized response
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");

            // Write a JSON response indicating that the JWT token is invalid or expired
            response.getWriter().write("{\"message\": \"Invalid or expired JWT token\"}");
        }
    }
}
