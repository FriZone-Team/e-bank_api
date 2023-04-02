package com.onionit.ebank.repository;

import com.onionit.ebank.model.role.Role;
import com.onionit.ebank.model.role.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(RoleName name);
}
