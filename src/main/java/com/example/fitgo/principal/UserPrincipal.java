package com.example.fitgo.principal;

import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.fitgo.model.Users;

public class UserPrincipal implements UserDetails {
    private Users user;

    public UserPrincipal(Users user) {

    }

    @java.lang.Override
    public java.util.Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("USER"));
    }

    @java.lang.Override
    public java.lang.String getPassword() {
        return user.getPassword();
    }

    @java.lang.Override
    public java.lang.String getUsername() {
        return user.getUsername();
    }

    @java.lang.Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @java.lang.Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @java.lang.Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @java.lang.Override
    public boolean isEnabled() {
        return true;
    }
}
