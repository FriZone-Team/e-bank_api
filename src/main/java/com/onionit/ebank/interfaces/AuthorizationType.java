package com.onionit.ebank.interfaces;

import lombok.Getter;
import org.jetbrains.annotations.Nullable;

public enum AuthorizationType {
    BASIC("Basic"),
    BEARER("Bearer"),
    ;
    @Getter
    private final String value;

    AuthorizationType(final String value) {
        this.value = value;
    }

    public static @Nullable AuthorizationType of(final String value) {
        for (AuthorizationType item : values()) {
            if (item.getValue().equals(value)) {
                return item;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.getValue();
    }
}
