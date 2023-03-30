package com.onionit.ebank.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseModel {
    @Getter
    @Setter
    @Column(unique = true)
    protected String username;

    @JsonIgnore
    @Getter
    @Setter
    @Column
    protected String password;
}
