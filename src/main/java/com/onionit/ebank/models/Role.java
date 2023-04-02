package com.onionit.ebank.models;

import com.fasterxml.jackson.annotation.JsonView;
import com.onionit.ebank.interfaces.View;
import com.onionit.ebank.rbac.BaseRole;
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
public class Role extends BaseModel implements BaseRole<Permission> {
    @Getter
    @Setter
    @Column(unique = true)
    protected String code;

    @JsonView(View.Admin.class)
    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.EAGER)
    protected Set<Permission> permissions;

    public Role(@NotNull String code) {
        this(code, Collections.emptySet());
    }
}
