package com.macedo.User.repository;

import java.util.Set;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import com.macedo.User.entities.Role;


public interface RoleRepository extends JpaRepository<Role, Integer> {
    Set<Role> findByRoleNameIn(List<String> names);
}
