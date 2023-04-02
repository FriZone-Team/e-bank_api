package com.onionit.ebank.models;

import com.onionit.ebank.rbac.BasePermission;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Permission extends BaseModel implements BasePermission {
    @Getter
    @Setter
    @Column(unique = true)
    protected String code;

    @Override
    public String getAuthority() {
        return this.getCode();
    }
}
