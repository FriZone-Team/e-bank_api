package com.onionit.ebank.exceptions;

import io.micrometer.common.lang.Nullable;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidException extends ResponseStatusException {
    @Getter
    protected final String property;

    @Getter
    protected final Object details;

    public InvalidException(String property, @Nullable Object details, String message) {
        super(HttpStatus.BAD_REQUEST, message);
        this.property = property;
        this.details = details;
    }
}
