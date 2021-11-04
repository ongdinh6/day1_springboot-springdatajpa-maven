package com.tma.demo.repositories.jpa;

import com.tma.demo.entities.jpa.RoleJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface IRoleJPARepository extends JpaRepository<RoleJPA, UUID> {
    RoleJPA getByRoleString(String role);
}
