package com.example.fitgo.principal;

import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.fitgo.model.Users;

/**
 * The UserPrincipal class implements the UserDetails interface from Spring
 * Security.
 * It is used to represent a user in the Spring Security context, providing
 * user-specific details.
 * The class encapsulates a Users object and maps it to the necessary properties
 * required by Spring Security.
 */
public class UserPrincipal implements UserDetails {

    // The Users object containing the user's data
    private Users user;

    /**
     * Constructor to initialize the UserPrincipal with a Users object.
     *
     * @param user The Users object containing user details.
     */
    public UserPrincipal(Users user) {
        this.user = user;
    }

    /**
     * Gets the authorities granted to the user.
     * This method returns a collection of GrantedAuthority that defines the
     * roles/permissions assigned to the user.
     * In this case, the user is granted a "USER" authority by default.
     * 
     * @return A collection of GrantedAuthority representing the user's roles.
     */
    @java.lang.Override
    public java.util.Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("USER"));
    }

    /**
     * Gets the password for the user.
     * This method returns the password of the user, which is stored in the `Users`
     * object.
     * 
     * @return The password of the user.
     */
    @java.lang.Override
    public java.lang.String getPassword() {
        return user.getPassword();
    }

    /**
     * Gets the username for the user.
     * This method returns the username of the user, which is stored in the `Users`
     * object.
     * 
     * @return The username of the user.
     */
    @java.lang.Override
    public java.lang.String getUsername() {
        return user.getUsername();
    }

    /**
     * Indicates whether the user's account has expired.
     * In this implementation, it always returns true, indicating that the account
     * is never expired.
     * 
     * @return true, indicating the account is not expired.
     */
    @java.lang.Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user's account is locked.
     * In this implementation, it always returns true, indicating that the account
     * is never locked.
     * 
     * @return true, indicating the account is not locked.
     */
    @java.lang.Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials (password) have expired.
     * In this implementation, it always returns true, indicating that the
     * credentials are never expired.
     * 
     * @return true, indicating the credentials are not expired.
     */
    @java.lang.Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user's account is enabled.
     * In this implementation, it always returns true, indicating that the account
     * is enabled.
     * 
     * @return true, indicating the account is enabled.
     */
    @java.lang.Override
    public boolean isEnabled() {
        return true;
    }
}
