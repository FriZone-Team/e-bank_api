package com.onionit.ebank.controllers.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class Credential {
    @Getter
    @Setter
    protected String username;

    @Getter
    @Setter
    protected String password;
}
