package com.licence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.licence.models.Role;


public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByCodeRole(String codeRole);
}
