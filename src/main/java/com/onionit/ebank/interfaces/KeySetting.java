package com.onionit.ebank.interfaces;

import lombok.Getter;

public enum KeySetting {
    IS_SEEDED("isSeeded"),
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
