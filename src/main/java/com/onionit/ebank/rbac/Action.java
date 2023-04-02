package com.onionit.ebank.rbac;

import java.util.HashSet;

public abstract class Action {
    public abstract String getCode();

    public HashSet<String> getAuthorities() {
        String code = this.getCode();
        HashSet<String> authorities = new HashSet<>();
        while (code.length() > 0) {
            authorities.add(code);
        }
        return authorities;
    }
}
