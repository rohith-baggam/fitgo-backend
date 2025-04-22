package com.example.fitgo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SecurityConfig is a configuration class that sets up the security settings
 * for the application.
 * It configures HTTP security, authentication provider, and session management,
 * along with JWT filter setup.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService; // Service to load user details from the database

    @Autowired
    private JwtFilter jwtFilter; // JWT filter to intercept requests and validate JWT tokens

    /**
     * Configures HTTP security settings for the application.
     * - Disables CSRF protection (typically for stateless APIs).
     * - Configures authorization rules for different endpoints.
     * - Sets session management policy to stateless, meaning the server doesn't
     * store session data.
     * - Adds JWT filter before the UsernamePasswordAuthenticationFilter to validate
     * JWT in requests.
     *
     * @param http The HttpSecurity object to configure security settings.
     * @return The configured SecurityFilterChain object.
     * @throws Exception If any error occurs during the configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Disable CSRF protection for stateless APIs (not required for JWT-based
        // authentication)
        http.csrf(customizer -> customizer.disable())

                // Configure authorization rules: allow public access to register and login,
                // others require authentication
                .authorizeHttpRequests(request -> request
                        .requestMatchers("register", "login") // Permit access to registration and login endpoints
                        .permitAll() // Allow all users to access register and login
                        .anyRequest() // All other requests must be authenticated
                        .authenticated())

                // Enable basic authentication (useful for some HTTP clients but optional with
                // JWT)
                .httpBasic(Customizer.withDefaults())

                // Set session creation policy to stateless: JWT will be used for
                // authentication, not sessions
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Add the JWT filter before UsernamePasswordAuthenticationFilter to validate
                // the JWT token
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build(); // Return the configured security filter chain
    }

    /**
     * Configures the AuthenticationProvider to authenticate users with their
     * username and password.
     * - Uses BCryptPasswordEncoder for password encoding.
     * - Specifies the UserDetailsService to load user data for authentication.
     *
     * @return The configured AuthenticationProvider.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12)); // Use BCrypt for strong password hashing
        provider.setUserDetailsService(userDetailsService); // Use the userDetailsService to load user information
        return provider;
    }

    /**
     * Configures the AuthenticationManager, which handles the authentication
     * process.
     * This manager is responsible for processing authentication requests.
     *
     * @param config The AuthenticationConfiguration object to obtain the
     *               authentication manager.
     * @return The configured AuthenticationManager.
     * @throws Exception If any error occurs while obtaining the
     *                   AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager(); // Return the authentication manager provided by Spring Security
    }
}
