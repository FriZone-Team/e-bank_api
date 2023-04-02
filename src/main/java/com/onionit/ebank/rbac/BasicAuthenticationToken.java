package com.onionit.ebank.rbac;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class BasicAuthenticationToken<T extends BaseUser<? extends BasePermission, ? extends BaseRole<? extends BasePermission>>> extends AuthenticationToken<T> {
    protected final Credential credential;

    public BasicAuthenticationToken(T user, Credential credential) {
        super(user);
        this.credential = credential;
    }

    public BasicAuthenticationToken(T user, String username, String password) {
        this(user, new Credential(username, password));
    }

    @Override
    public Credential getCredentials() {
        return this.credential;
    }

    @AllArgsConstructor
    public static class Credential {
        @Getter
        protected String username;

        @Getter
        protected String password;
    }
}
