package com.onionit.ebank.services;

import com.onionit.ebank.interfaces.KeySetting;
import com.onionit.ebank.models.Setting;
import com.onionit.ebank.repositories.SettingRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class SettingService extends RepositoryService<Setting, SettingRepository> {
    @Autowired
    public SettingService(SettingRepository repository) {
        super(repository);
    }

    public String get(@NotNull KeySetting key) {
        return this.get(key, null);
    }

    public String get(@NotNull KeySetting key, String defaultValue) {
        Optional<Setting> s = this.getRepository().findById(key.toString());
        if (s.isPresent()) {
            return s.get().getValue();
        }
        String valueFromEnv = System.getenv(key.name());
        if (!Objects.isNull(valueFromEnv)) {
            this.set(key, valueFromEnv);
            return valueFromEnv;
        }
        this.set(key, defaultValue);
        return defaultValue;
    }

    public void set(@NotNull KeySetting key, String value) {
        this.getRepository().save(new Setting(key, value));
    }

    public boolean isEnabled(KeySetting key) {
        return this.get(key, "false").equals("true");
    }

    public void enable(KeySetting key) {
        this.set(key, "true");
    }

    public void disable(KeySetting key) {
        this.set(key, "false");
    }

    public boolean isMarked(KeySetting key) {
        return this.get(key, "unmarked").equals("marked");
    }

    public void mark(KeySetting key) {
        this.set(key, "marked");
    }
}
