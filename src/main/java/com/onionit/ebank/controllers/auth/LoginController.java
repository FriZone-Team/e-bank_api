package com.onionit.ebank.controllers.auth;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.annotation.JsonView;
import com.onionit.ebank.controllers.annotations.AuthController;
import com.onionit.ebank.interfaces.View;
import com.onionit.ebank.models.User;
import com.onionit.ebank.services.AuthenticationService;
import com.onionit.ebank.services.UserService;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@AuthController
@RequestMapping("login")
public class LoginController {
    @Getter
    protected final AuthenticationService authenticationService;

    @Getter
    protected final UserService userService;

    @Autowired
    public LoginController(AuthenticationService authenticationService, UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @JsonView(View.Detail.class)
    @PostMapping
    public ResponseEntity<SessionInfo> login(@RequestBody() @NotNull Credential credential) {
        return ResponseEntity.ok(this.login(credential.getUsername(), credential.getPassword()));
    }

    public SessionInfo login(String username, String password) {
        Optional<User> userOptional = this.getUserService().find(username);
        if (userOptional.isPresent()) {
            User user = userOptional.orElseThrow();
            if (this.getUserService().checkPassword(user, password)) {
                String token = this.getAuthenticationService().generate(user.getId());
                DecodedJWT data = this.getAuthenticationService().parse(token);
                return new SessionInfo(token, user, data);
            }
            throw new InvalidPasswordException(username, password);
        }
        throw new InvalidUsernameException(username);
    }
}
