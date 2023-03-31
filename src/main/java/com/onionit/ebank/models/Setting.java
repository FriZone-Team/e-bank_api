package com.onionit.ebank.models;

import com.onionit.ebank.interfaces.KeySetting;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Setting {
    @Getter
    @Setter
    @Id
    @Column(unique = true)
    protected String name;

    @Getter
    @Setter
    @Column
    protected String value;

    public Setting(@NotNull KeySetting name, String value) {
        this(name.toString(), value);
    }
}
