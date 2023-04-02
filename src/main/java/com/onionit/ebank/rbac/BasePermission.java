package com.onionit.ebank.rbac;

import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.GrantedAuthority;

public interface BasePermission extends GrantedAuthority, AuthenticationVoter {
    @Override
    default boolean isAuthorizationFor(@NotNull Action action) {
        for (String authority : action.getAuthorities()) {
            if (this.getAuthority().equals(authority)) {
                return true;
            }
        }
        return false;
    }
}
