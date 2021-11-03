package com.tma.demo.services.cassandra.impls;

import com.tma.demo.dtos.requests.ProductRequest;
import com.tma.demo.dtos.responses.ProductResponse;
import com.tma.demo.entities.cassandra.Product;
import com.tma.demo.exceptions.InternalServerException;
import com.tma.demo.exceptions.NotFoundException;
import com.tma.demo.repositories.cassandra.IProductCassandraRepository;
import com.tma.demo.services.cassandra.IProductCassandraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductCassandraServiceImpl implements IProductCassandraService {
    @Autowired
    IProductCassandraRepository productCassandraRepository;

    @Override
    public ProductResponse save(ProductRequest productRequest) {
        Product productToSave;
        if (null == (productToSave = productCassandraRepository.save(productRequest.toCassandraObject()))) {
            throw new InternalServerException("Server was be failed to save product !");
        } else {
            return productToSave.toProductResponse();
        }
    }

    @Override
    public List<ProductResponse> getAllProduct() {
        List<ProductResponse> products = new ArrayList<>();
        productCassandraRepository.findAll().forEach(product -> {
            products.add(product.toProductResponse());
        });
        return products;
    }

    @Override
    public Product getById(UUID productId) {
        Product product = productCassandraRepository.findById(productId).get();
        if(null == product) {
            throw new NotFoundException("Product not found with id: "+productId);
        }else {
            return product;
        }
    }
}
