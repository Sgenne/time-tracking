package com.sgenne.timetracking.security.permission;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

public class PermissionHandler implements PermissionEvaluator {
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
       /* if (authentication == null || targetDomainObject == null) return false;

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                (UsernamePasswordAuthenticationToken) authentication;

        */


        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
