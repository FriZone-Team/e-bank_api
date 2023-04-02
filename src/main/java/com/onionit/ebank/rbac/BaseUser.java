package com.onionit.ebank.rbac;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.onionit.ebank.interfaces.View;
import org.hibernate.collection.spi.PersistentSet;
import org.springframework.security.core.AuthenticatedPrincipal;

import java.util.HashSet;
import java.util.Set;

public interface BaseUser<P extends BasePermission, R extends BaseRole<P>> extends AuthenticatedPrincipal {
    Set<R> getRoles();

    void setRoles(Set<R> roles);

    @JsonIgnore
    default Set<R> getRolesEditable() {
        if (this.getRoles() instanceof PersistentSet<R>) {
            this.setRoles(new HashSet<>(this.getRoles()));
        }
        return this.getRoles();
    }

    default void addRole(R role) {
        this.getRolesEditable().add(role);
    }

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

    @JsonView({View.Admin.class, View.Owned.class})
    default HashSet<P> getAllPermissions() {
        HashSet<P> permissions = new HashSet<>(this.getPermissions());
        for (R role : this.getRoles()) {
            permissions.addAll(role.getPermissions());
        }
        return permissions;
    }
}
