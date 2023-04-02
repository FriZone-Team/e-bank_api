package com.onionit.ebank.rbac;

import com.auth0.jwt.interfaces.DecodedJWT;

public class BearerAuthenticationToken<T extends BaseUser<? extends BasePermission, ? extends BaseRole<? extends BasePermission>>> extends AuthenticationToken<T> {
    protected final DecodedJWT credential;

    public BearerAuthenticationToken(T user, DecodedJWT credential) {
        super(user);
        this.credential = credential;
    }

    @Override
    public Object getCredentials() {
        return null;
    }
}
