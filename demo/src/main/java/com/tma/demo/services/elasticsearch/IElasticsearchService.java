package com.tma.demo.services.elasticsearch;

import com.tma.demo.entities.elasticsearch.ProductElasticsearch;

import java.util.UUID;

public interface IElasticsearchService {
    ProductElasticsearch findById(String id);
    ProductElasticsearch save(ProductElasticsearch productElasticsearch);
}
