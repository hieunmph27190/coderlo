package com.hieucoder.coderlo.repository;

import com.hieucoder.coderlo.entity.Permission;
import com.hieucoder.coderlo.entity.Role;
import com.hieucoder.coderlo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {
    public boolean existsByName(String name);

    public Optional<Permission> findByName(String name);
}
