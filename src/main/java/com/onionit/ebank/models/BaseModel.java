package com.onionit.ebank.models;

import jakarta.persistence.*;
import lombok.Getter;

@MappedSuperclass
public abstract class BaseModel {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column
    protected String id;
}
