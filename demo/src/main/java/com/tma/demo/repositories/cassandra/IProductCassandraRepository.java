package com.tma.demo.repositories.cassandra;

import com.tma.demo.entities.cassandra.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface IProductCassandraRepository extends CrudRepository<Product, UUID> {
}