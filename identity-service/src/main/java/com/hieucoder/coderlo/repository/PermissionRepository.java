package com.hieucoder.coderlo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hieucoder.coderlo.entity.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {
    public boolean existsByName(String name);

    public Optional<Permission> findByName(String name);
}
