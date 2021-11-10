package com.tma.demo.controllers.elasticsearch;

import com.tma.demo.entities.elasticsearch.ProductElasticsearch;
import com.tma.demo.repositories.elasticsearch.IProductElasticsearchtRepository;
import com.tma.demo.services.elasticsearch.IElasticsearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/elasticsearch")
public class ElasticsearchController {
    @Autowired
    IElasticsearchService elasticsearchtRepository;

    @GetMapping("")
    public ProductElasticsearch getProductElasticsearch(@RequestParam String id){

        return elasticsearchtRepository.findById(id);
    }
    @PostMapping("")
    public ProductElasticsearch postNewProduct(@RequestBody ProductElasticsearch productElasticsearch){

        return elasticsearchtRepository.save(productElasticsearch);
    }
}
