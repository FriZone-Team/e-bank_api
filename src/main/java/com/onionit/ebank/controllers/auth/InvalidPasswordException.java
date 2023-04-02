package com.onionit.ebank.controllers.auth;

import com.onionit.ebank.exceptions.InvalidException;

public class InvalidPasswordException extends InvalidException {
    public InvalidPasswordException(String username, String password) {
        super("password", new Credential(username, password), "Invalid password");
    }
}
