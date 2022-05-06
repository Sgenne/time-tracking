package com.sgenne.timetracking.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.transaction.Transactional;

@AllArgsConstructor
@Getter
public enum Role {
    USER("USER"), ADMIN("ADMIN");

    private final String role;
}
