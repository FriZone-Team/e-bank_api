package com.onionit.ebank.services;

import com.onionit.ebank.repositories.BaseRepository;
import lombok.Getter;

public abstract class RepositoryService<M, R extends BaseRepository<M>> {
    @Getter
    protected final R repository;

    public RepositoryService(R repository) {
        this.repository = repository;
    }
}
