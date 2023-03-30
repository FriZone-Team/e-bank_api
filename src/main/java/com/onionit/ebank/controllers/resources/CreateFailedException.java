package com.onionit.ebank.controllers.resources;

import com.onionit.ebank.exceptions.AppException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CreateFailedException extends AppException {
    public CreateFailedException(String message) {
        super(message);
    }
}
