package com.sgenne.timetracking.user.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

public enum Role implements GrantedAuthority {
    USER("USER"), ADMIN("ADMIN");

    private final String authority;

    Role(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
