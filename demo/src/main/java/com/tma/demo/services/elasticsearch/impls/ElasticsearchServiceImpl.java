package com.tma.demo.services.elasticsearch.impls;

import com.tma.demo.entities.elasticsearch.ProductElasticsearch;
import com.tma.demo.repositories.elasticsearch.IProductElasticsearchtRepository;
import com.tma.demo.services.elasticsearch.IElasticsearchService;
import com.tma.demo.utils.UUIDHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ElasticsearchServiceImpl implements IElasticsearchService {
    @Autowired
    IProductElasticsearchtRepository repo;

    @Override
    public ProductElasticsearch findById(String id) {
        return repo.findByProductId(UUIDHelper.toUUID(id));
    }

    @Override
    public ProductElasticsearch save(ProductElasticsearch productElasticsearch) {
        return repo.save(productElasticsearch);
    }
}
