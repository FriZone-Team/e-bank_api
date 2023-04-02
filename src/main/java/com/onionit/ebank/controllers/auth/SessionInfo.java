package com.onionit.ebank.controllers.auth;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onionit.ebank.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class SessionInfo {
    @Getter
    protected String token;

    @Getter
    protected User user;

    @JsonIgnore
    @Getter
    protected DecodedJWT data;

    public String getIssuedAt() {
        return this.getData().getIssuedAt().toString();
    }

    public String getExpiresAt() {
        return this.getData().getExpiresAt().toString();
    }
}
