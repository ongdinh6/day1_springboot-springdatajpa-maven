package com.tma.demo.repositories.cassandra;

import com.tma.demo.entities.cassandra.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IProductCassandraRepository extends CrudRepository<Product, UUID> {
    Product findByClazz(String clazz);
    Product getByProductId(UUID id);
}