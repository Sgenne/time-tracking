package com.sgenne.timetracking.user;

import lombok.Getter;

public enum Role {
    USER("USER"), ADMIN("ADMIN");

    @Getter
    private final String role;

    Role(String role) {
        this.role = role;
    }
}
