package com.onionit.ebank.interfaces;

import lombok.Getter;
import org.jetbrains.annotations.Nullable;

public enum KeySetting {
    IS_SEEDED("isSeeded"),
    ADMIN_DEFAULT_PASSWORD("adminDefaultPassword"),
    USER_USERNAME("userUsername"),
    USER_DEFAULT_PASSWORD("userDefaultPassword"),
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
