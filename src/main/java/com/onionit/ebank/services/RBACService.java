package com.onionit.ebank.services;

import com.onionit.ebank.interfaces.OnInitializer;
import com.onionit.ebank.models.Permission;
import com.onionit.ebank.models.Role;
import com.onionit.ebank.rbac.PermissionCode;
import com.onionit.ebank.rbac.RoleCode;
import com.onionit.ebank.repositories.PermissionRepository;
import com.onionit.ebank.repositories.RoleRepository;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class RBACService implements OnInitializer {
    @Getter
    protected final SettingService settingService;

    @Getter
    protected final RoleRepository roleRepository;

    @Getter
    protected final PermissionRepository permissionRepository;

    @Autowired
    public RBACService(SettingService settingService, RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.settingService = settingService;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    protected Permission getOrCreatePermission(String code) {
        Optional<Permission> permission = this.getPermissionRepository().findByCode(code);
        return permission.orElseGet(() -> this.getPermissionRepository().save(new Permission(code)));
    }

    protected Permission getPermissionFromCode(@NotNull PermissionCode code) {
        return this.getPermissionRepository().findByCode(code.name()).orElseThrow();
    }

    protected Role getOrCreateRole(String code) {
        Optional<Role> role = this.getRoleRepository().findByCode(code);
        return role.orElseGet(() -> this.getRoleRepository().save(new Role(code)));
    }

    protected Role getRoleFromCode(@NotNull RoleCode code) {
        return this.getRoleRepository().findByCode(code.name()).orElseThrow();
    }

    public void onInitializer() {
        Map<PermissionCode, Permission> permissions = new HashMap<>();
        for (PermissionCode code : PermissionCode.values()) {
            Permission permission = this.getOrCreatePermission(code.name());
            permissions.put(code, permission);
        }
        for (RoleCode code : RoleCode.values()) {
            Role role = this.getOrCreateRole(code.name());
            for (PermissionCode permission : code.getPermissions()) {
                role.addPermission(permissions.get(permission));
            }
            this.getRoleRepository().save(role);
        }
    }
}
