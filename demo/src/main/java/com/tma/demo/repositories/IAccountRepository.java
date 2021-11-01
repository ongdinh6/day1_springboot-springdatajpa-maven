package com.tma.demo.repositories;

import com.tma.demo.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IAccountRepository extends JpaRepository<Account, UUID> {
}
