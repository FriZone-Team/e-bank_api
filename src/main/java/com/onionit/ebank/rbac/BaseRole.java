package com.onionit.ebank.rbac;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.collection.spi.PersistentSet;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public interface BaseRole<P extends BasePermission> extends AuthenticationVoter {
    Set<P> getPermissions();

    void setPermissions(Set<P> permissions);

    @JsonIgnore
    default Set<P> getPermissionsEditable() {
        if (this.getPermissions() instanceof PersistentSet<P>) {
            this.setPermissions(new HashSet<>(this.getPermissions()));
        }
        return this.getPermissions();
    }

    default void addPermission(P permission) {
        this.getPermissionsEditable().add(permission);
    }

    @Override
    default boolean isAuthorizationFor(@NotNull Action action) {
        for (BasePermission permission : this.getPermissions()) {
            if (permission.isAuthorizationFor(action)) {
                return true;
            }
        }
        return false;
    }
}
