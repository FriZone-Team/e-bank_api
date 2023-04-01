package com.onionit.ebank.filters;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.onionit.ebank.interfaces.AuthorizationType;
import com.onionit.ebank.models.User;
import com.onionit.ebank.rbac.AuthenticationToken;
import com.onionit.ebank.rbac.BasicAuthenticationToken;
import com.onionit.ebank.rbac.BearerAuthenticationToken;
import com.onionit.ebank.services.AuthenticationService;
import com.onionit.ebank.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {
    protected static final String HEADER_NAME = "Authorization";
    protected static final String RESPONSE_HEADER_NAME = "X-User";

    @Getter
    protected final AuthenticationService authenticationService;

    @Getter
    protected final UserService userService;

    @Autowired
    public AuthenticationFilter(AuthenticationService authenticationService, UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    protected Map<AuthorizationType, List<String>> getProperties(@NotNull HttpServletRequest request) {
        MultiValueMap<AuthorizationType, String> properties = new LinkedMultiValueMap<>();
        for (String header : Collections.list(request.getHeaders(HEADER_NAME))) {
            if (!Objects.isNull(header)) {
                Matcher matcher = Pattern.compile("^([A-z]+)(?: +(.+)?)?").matcher(header);
                if (matcher.find()) {
                    AuthorizationType authorizationType = AuthorizationType.of(matcher.group(1));
                    if (!Objects.isNull(authorizationType)) {
                        properties.add(authorizationType, matcher.group(2));
                    }
                }
            }
        }
        return properties;
    }

    protected BasicAuthenticationToken<User> getFromBasicAuthorization(@NotNull String value) {
        String valDecoded = new String(Base64.getDecoder().decode(value));
        Matcher matcher = Pattern.compile("^(.+):(.+)$").matcher(valDecoded);
        if (matcher.find()) {
            Optional<User> userOptional = this.userService.findByUsername(matcher.group(1));
            if (userOptional.isPresent()) {
                User user = userOptional.orElseThrow();
                if (this.userService.checkPassword(user, matcher.group(2))) {
                    return new BasicAuthenticationToken<>(user, matcher.group(1), matcher.group(2));
                }
            }
        }
        return null;
    }

    protected BearerAuthenticationToken<User> getFromTokenBearer(@NotNull String value) {
        if (this.authenticationService.validate(value)) {
            DecodedJWT data = this.authenticationService.parse(value);
            Optional<User> userOptional = this.userService.find(data.getSubject());
            if (userOptional.isPresent()) {
                User user = userOptional.orElseThrow();
                return new BearerAuthenticationToken<>(user, data);
            }
        }
        return null;
    }

    protected AuthenticationToken<User> getToken(@NotNull HttpServletRequest request) {
        AuthenticationToken<User> token;
        for (Map.Entry<AuthorizationType, List<String>> item : this.getProperties(request).entrySet()) {
            switch (item.getKey()) {
                case BASIC -> {
                    for (String v : item.getValue()) {
                        token = this.getFromBasicAuthorization(v);
                        if (!Objects.isNull(token)) {
                            return token;
                        }
                    }
                }
                case BEARER -> {
                    for (String v : item.getValue()) {
                        token = this.getFromTokenBearer(v);
                        if (!Objects.isNull(token)) {
                            return token;
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        AuthenticationToken<User> token = this.getToken(request);
        if (!Objects.isNull(token)) {
            response.addHeader(RESPONSE_HEADER_NAME, token.getName());
            SecurityContextHolder.getContext().setAuthentication(token);
        }
        filterChain.doFilter(request, response);
    }
}
