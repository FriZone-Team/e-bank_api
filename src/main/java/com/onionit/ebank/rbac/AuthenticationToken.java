package com.onionit.ebank.rbac;

import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Set;

public abstract class AuthenticationToken<T extends BaseUser<? extends BasePermission, ? extends BaseRole<? extends BasePermission>>> extends AbstractAuthenticationToken {
    public AuthenticationToken(Set<? extends BasePermission> permissions) {
        super(permissions);
        this.setAuthenticated(!permissions.isEmpty());
    }

    public AuthenticationToken(@NotNull T user) {
        this(user.getAllPermissions());
        this.setDetails(user);
    }

    @Override
    public T getDetails() {
        //noinspection unchecked
        return (T) super.getDetails();
    }

    @Override
    public String getPrincipal() {
        return this.getDetails().getName();
    }
}
