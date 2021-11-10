package com.tma.demo.repositories.elasticsearch;

import com.tma.demo.entities.elasticsearch.ProductElasticsearch;
import jnr.ffi.provider.InAccessibleMemoryIO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IProductElasticsearchtRepository extends ElasticsearchRepository<ProductElasticsearch, UUID> {
    ProductElasticsearch findByProductId(UUID id);
}
