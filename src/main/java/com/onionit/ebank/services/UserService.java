package com.onionit.ebank.services;

import com.onionit.ebank.interfaces.KeySetting;
import com.onionit.ebank.interfaces.OnInitializer;
import com.onionit.ebank.models.User;
import com.onionit.ebank.rbac.RoleCode;
import com.onionit.ebank.repositories.UserRepository;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService extends RepositoryService<User, UserRepository> implements OnInitializer {
    @Getter
    protected final RBACService rbacService;

    @Getter
    protected final SettingService settingService;

    @Getter
    protected final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository repository, RBACService rbacService, SettingService settingService, PasswordEncoder passwordEncoder) {
        super(repository);
        this.rbacService = rbacService;
        this.settingService = settingService;
        this.passwordEncoder = passwordEncoder;
    }

    protected User getOrCreateUser(String username, String password) {
        Optional<User> user = this.getRepository().findByUsername(username);
        return user.orElseGet(() -> this.getRepository().save(new User(username, this.getPasswordEncoder().encode(password))));
    }

    public Optional<User> find(String id) {
        return this.getRepository().findById(id);
    }

    public Optional<User> findByUsername(String username) {
        return this.getRepository().findByUsername(username);
    }

    public boolean checkPassword(@NotNull User user, String password) {
        return this.getPasswordEncoder().matches(password, user.getPassword());
    }

    public boolean checkPassword(@NotNull String username, String password) {
        return this.checkPassword(this.findByUsername(username).orElseThrow(), password);
    }

    public void onInitializer() {
        User admin = this.getOrCreateUser("admin", this.settingService.get(KeySetting.ADMIN_DEFAULT_PASSWORD, "admin"));
        User user = this.getOrCreateUser(this.settingService.get(KeySetting.USER_USERNAME, "user"), this.settingService.get(KeySetting.USER_DEFAULT_PASSWORD, "user"));
        admin.addRole(this.rbacService.getRoleFromCode(RoleCode.SUPER_ADMIN));
        this.getRepository().save(admin);
    }
}
