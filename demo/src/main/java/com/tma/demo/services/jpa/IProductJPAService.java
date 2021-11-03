package com.tma.demo.services.jpa;

import com.tma.demo.dtos.requests.ProductRequest;
import com.tma.demo.dtos.responses.ProductResponse;
import com.tma.demo.entities.cassandra.Product;

import java.util.List;

public interface IProductJPAService {
    ProductResponse save(Product productCassandra);
    List<ProductResponse> getAllProduct();
    ProductResponse getById(String productId);
}
