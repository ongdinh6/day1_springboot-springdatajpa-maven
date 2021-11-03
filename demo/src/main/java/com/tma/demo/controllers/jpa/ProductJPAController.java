package com.tma.demo.controllers.jpa;

import com.tma.demo.dtos.DataResponse;
import com.tma.demo.dtos.requests.ProductRequest;
import com.tma.demo.dtos.requests.RequestSingleBodyType;
import com.tma.demo.dtos.responses.ProductResponse;
import com.tma.demo.entities.cassandra.Product;
import com.tma.demo.services.cassandra.IProductCassandraService;
import com.tma.demo.services.jpa.IProductJPAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
public class ProductJPAController {
    @Autowired
    IProductJPAService productJPAService;
    @Autowired
    IProductCassandraService productCassandraService;

    @PostMapping("/new")
    public DataResponse<ProductResponse> addNewProduct(@RequestBody RequestSingleBodyType<String> productId) {
        Product productCassandra = productCassandraService.getById(UUID.fromString(productId.getT()));
        ProductResponse productResponse = productJPAService.save(productCassandra);
        return new DataResponse<>(productResponse, HttpStatus.OK, "Add new a product into postgresDB is successful!");
    }

    @GetMapping("")
    public DataResponse<List<ProductResponse>> getAllProduct() {
        return new DataResponse<>(productJPAService.getAllProduct(), HttpStatus.OK, "Get list product from postgresDB is successful!");
    }

    @GetMapping("/{id}")
    public DataResponse<ProductResponse> getProductById(@PathVariable String id){
        return new DataResponse<>(productJPAService.getById(id), HttpStatus.OK, "Get product with id: "+id+" is successful!");
    }
}
