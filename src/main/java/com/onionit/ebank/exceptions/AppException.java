package com.onionit.ebank.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@JsonIgnoreProperties({"stackTrace", "cause", "suppressed"})
public class AppException extends Exception {
    @Getter
    protected String code;

    @Getter
    protected String message;

    public AppException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public AppException(String message) {
        this.code = this.getClass().getSimpleName();
        this.message = message;
    }
}
