package com.onionit.ebank.services;

import com.onionit.ebank.interfaces.KeySetting;
import com.onionit.ebank.interfaces.OnInitializer;
import com.onionit.ebank.models.User;
import com.onionit.ebank.repositories.UserRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserService extends RepositoryService<User, UserRepository> implements OnInitializer {
    @Getter
    protected final SettingService settingService;

    @Getter
    protected final PasswordEncoder passwordEncoder;
    @Autowired
    public UserService(UserRepository repository, SettingService settingService, PasswordEncoder passwordEncoder) {
        super(repository);
        this.settingService = settingService;
        this.passwordEncoder = passwordEncoder;
    }

    protected User newUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(this.getPasswordEncoder().encode(password));
        return user;
    }

    public void onInitializer() {
        this.getRepository().saveAll(
                Arrays.asList(
                        this.newUser("admin", this.settingService.get(KeySetting.ADMIN_DEFAULT_PASSWORD, "admin")),
                        this.newUser(this.settingService.get(KeySetting.USER_USERNAME, "user"), this.settingService.get(KeySetting.USER_DEFAULT_PASSWORD, "user"))
                )
        );
    }
}
