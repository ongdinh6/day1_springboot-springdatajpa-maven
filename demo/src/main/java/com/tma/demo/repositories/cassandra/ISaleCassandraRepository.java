package com.tma.demo.repositories.cassandra;

import com.tma.demo.entities.cassandra.Product;
import com.tma.demo.entities.cassandra.Sale;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ISaleCassandraRepository extends CrudRepository<Sale, UUID> {
}