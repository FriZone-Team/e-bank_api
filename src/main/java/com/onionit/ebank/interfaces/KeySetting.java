package com.onionit.ebank.interfaces;

import lombok.Getter;

public enum KeySetting {
    IS_SEEDED("isSeeded"),
    ADMIN_DEFAULT_PASSWORD("adminDefaultPassword"),
    USER_USERNAME("userUsername"),
    USER_DEFAULT_PASSWORD("userDefaultPassword"),
    AUTHENTICATION_SECRET("authenticationSecret"),
    AUTHENTICATION_EXPIRATION("authenticationExpiration"),
    ;

    @Getter
    private final String value;

    KeySetting(final String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }
}
