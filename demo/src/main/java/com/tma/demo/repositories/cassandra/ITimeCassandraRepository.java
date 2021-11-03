package com.tma.demo.repositories.cassandra;

import com.tma.demo.entities.cassandra.Product;
import com.tma.demo.entities.cassandra.Time;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ITimeCassandraRepository extends CrudRepository<Time, UUID> {
}