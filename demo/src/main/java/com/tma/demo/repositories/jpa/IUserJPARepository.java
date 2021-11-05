package com.tma.demo.repositories.jpa;

import com.tma.demo.entities.jpa.UserJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IUserJPARepository extends JpaRepository<UserJPA, UUID> {
    UserJPA findByUsername(String username);
    UserJPA findByUserId(UUID userId);
}
