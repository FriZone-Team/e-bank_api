package com.onionit.ebank.repositories;

import com.onionit.ebank.models.Permission;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepository extends BaseRepository<Permission> {
    Optional<Permission> findByCode(String code);
}
