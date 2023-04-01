package com.onionit.ebank.controllers.auth;

import com.onionit.ebank.controllers.annotations.AuthController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AuthController
@RequestMapping("login")
public class LoginController {
    @PostMapping
    public String check() {
        return "OK";
    }
}
