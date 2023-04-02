package com.onionit.ebank.controllers.auth;

import com.onionit.ebank.exceptions.InvalidException;

public class InvalidUsernameException extends InvalidException {
    public InvalidUsernameException(String username) {
        super("username", username, "Invalid username");
    }
}
