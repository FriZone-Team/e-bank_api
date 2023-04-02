package com.onionit.ebank.rbac;

public interface AuthenticationVoter {
    boolean isAuthorizationFor(Action action);
}
