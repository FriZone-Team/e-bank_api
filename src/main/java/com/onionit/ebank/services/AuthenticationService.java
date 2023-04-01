package com.onionit.ebank.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.onionit.ebank.interfaces.KeySetting;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

@Service
public class AuthenticationService {
    protected static long DEFAULT_EXPIRATION = 10 * 60 * 1000;

    @Getter
    @Setter
    protected Algorithm algorithm;

    @Getter
    @Setter
    protected long expiration;

    @Autowired
    public AuthenticationService(@NotNull SettingService settingService) {
        this.expiration = Long.parseLong(settingService.get(KeySetting.AUTHENTICATION_EXPIRATION, String.valueOf(DEFAULT_EXPIRATION)));
        this.algorithm = Algorithm.HMAC512(Objects.requireNonNull(settingService.get(KeySetting.AUTHENTICATION_SECRET)));
    }

    public String generate(@NotNull String userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + this.getExpiration());
        return JWT.create()
                .withSubject(userId)
                .withIssuedAt(now)
                .withExpiresAt(expiryDate)
                .sign(this.getAlgorithm());
    }

    public boolean validate(@NotNull String token) {
        try {
            this.parse(token);
            return true;
        } catch (JWTVerificationException ex) {
            return false;
        }
    }

    public DecodedJWT parse(@NotNull String token) {
        return JWT.require(this.getAlgorithm()).build().verify(token);
    }

    public String parseSubject(@NotNull String token) {
        return this.parse(token).getSubject();
    }
}
