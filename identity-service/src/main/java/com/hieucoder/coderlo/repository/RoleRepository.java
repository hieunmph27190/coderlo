package com.hieucoder.coderlo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hieucoder.coderlo.entity.Role;
import com.hieucoder.coderlo.entity.User;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    public boolean existsByName(String name);

    public Optional<User> findByName(String name);
}
