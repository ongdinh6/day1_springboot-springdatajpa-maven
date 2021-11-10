package com.tma.demo.services.cassandra;

import com.tma.demo.dtos.requests.ProductRequest;
import com.tma.demo.dtos.responses.ProductResponse;
import com.tma.demo.entities.cassandra.Product;

import java.util.List;
import java.util.UUID;

public interface IProductCassandraService {
    ProductResponse save(ProductRequest productRequest);
    List<ProductResponse> getAllProduct();
    Product getById(UUID productId);
    Product findByClazz(String clazz);
}
