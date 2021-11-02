package com.tma.demo.repositories;

import com.tma.demo.entities.Account;
import org.springframework.data.repository.CrudRepository;
import java.util.UUID;

public interface IAccountCassandraRepository extends CrudRepository<Account, UUID> {
}