package com.sgenne.timetracking.security;

import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserAuthentication implements Authentication {

    @Getter
    private final String username;
    @Getter
    private final String password;
    @Getter
    private final Long userId;
    private final Collection<? extends GrantedAuthority> authorities;
    private boolean isAuthenticated = false;

    public UserAuthentication(String username, String password, Long userId, Collection<? extends GrantedAuthority> authorities, boolean isAuthenticated) {
        this.username = username;
        this.password = password;
        this.userId = userId;
        this.authorities = authorities;
        this.isAuthenticated = isAuthenticated;
    }

    public UserAuthentication(String username, String password, Long userId, boolean isAuthenticated) {
        this.username = username;
        this.password = password;
        this.userId = userId;
        this.isAuthenticated = isAuthenticated;
        this.authorities = List.of();
    }

    public UserAuthentication(String username, String password, Long userId) {
        this.username = username;
        this.password = password;
        this.userId = userId;
        this.authorities = List.of();
    }

    public UserAuthentication(String username, String password, Long userId, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.userId = userId;
        this.authorities = authorities;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities
                .stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                .toList();
    }

    @Override
    public Object getCredentials() {
        return password;
    }

    @Override
    public Object getDetails() {
        return userId;
    }

    @Override
    public Object getPrincipal() {
        return username;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
     this.isAuthenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return null;
    }
}
