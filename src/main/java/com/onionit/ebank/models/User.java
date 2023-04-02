package com.onionit.ebank.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.onionit.ebank.interfaces.View;
import com.onionit.ebank.rbac.BaseUser;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseModel implements BaseUser<Permission, Role> {
    @Getter
    @Setter
    @Column(unique = true)
    protected String username;

    @JsonIgnore
    @Getter
    @Setter
    @Column
    protected String password;

    @JsonView(View.Detail.class)
    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.EAGER)
    protected Set<Role> roles;

    @JsonView({View.Admin.class, View.Owned.class})
    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.EAGER)
    protected Set<Permission> permissions;

    public User(@NotNull String username, @NotNull String password) {
        this(username, password, Collections.emptySet(), Collections.emptySet());
    }

    @JsonIgnore
    @Override
    public String getName() {
        return this.getId();
    }
}
