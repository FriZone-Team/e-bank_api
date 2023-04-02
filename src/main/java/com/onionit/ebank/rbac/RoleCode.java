package com.onionit.ebank.rbac;

import lombok.Getter;

import java.util.Set;

public enum RoleCode {
    SUPER_ADMIN(PermissionCode.values()),
    HEALTH(PermissionCode.HEALTH_LIVENESS, PermissionCode.HEALTH_READINESS, PermissionCode.HEALTH_STARTUP),
    ;

    @Getter
    private final Set<PermissionCode> permissions;

    RoleCode(PermissionCode... permissions) {
        this.permissions = Set.of(permissions);
    }
}
