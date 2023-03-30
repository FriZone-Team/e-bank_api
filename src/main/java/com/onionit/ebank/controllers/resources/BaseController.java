package com.onionit.ebank.controllers.resources;

import com.onionit.ebank.repositories.BaseRepository;
import com.onionit.ebank.services.RepositoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

public abstract class BaseController<M, S extends RepositoryService<M, ?>> {
    protected final S service;

    public BaseController(S service) {
        this.service = service;
    }

    protected BaseRepository<M> getRepository() {
        return this.service.getRepository();
    }

    @GetMapping
    public Page<M> all(Pageable pageable) {
        return this.getRepository().findAll(pageable);
    }

    @GetMapping("{id}")
    public ResponseEntity<M> detail(@PathVariable("id") String id) {
        Optional<M> instance = this.getRepository().findById(id);
        return instance.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<M> create(@RequestBody() M data) throws CreateFailedException {
        try {
            if (!Objects.isNull(data)) {
                M instance = this.getRepository().save(data);
                return ResponseEntity.ok(instance);
            }
        } catch (Exception ex) {
            throw new CreateFailedException(ex.getMessage());
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("{id}")
    public Optional<M> replace(@PathVariable("id") String id, @RequestBody() M data) {
        Optional<M> instance = this.getRepository().findById(id);
        if (instance.isPresent()) {
            this.getRepository().save(data);
        }
        return instance;
    }

    @PatchMapping("{id}")
    public Optional<M> update(@PathVariable("id") String id, @RequestBody() M data) {
        Optional<M> instance = this.getRepository().findById(id);
        if (instance.isPresent()) {
            this.getRepository().save(data);
        }
        return instance;
    }


    @DeleteMapping("{id}")
    public Optional<M> delete(@PathVariable("id") String id) {
        Optional<M> instance = this.getRepository().findById(id);
        if (instance.isPresent()) {
            this.getRepository().deleteById(id);
        }
        return instance;
    }
}
