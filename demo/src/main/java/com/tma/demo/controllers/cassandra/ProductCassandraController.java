package com.tma.demo.controllers.cassandra;

import com.tma.demo.dtos.DataResponse;
import com.tma.demo.dtos.requests.ProductRequest;
import com.tma.demo.dtos.responses.ProductResponse;
import com.tma.demo.services.cassandra.IProductCassandraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cassandra/products")
public class ProductCassandraController {
    @Autowired
    IProductCassandraService productCassandraService;

    @PostMapping("/new")
    public DataResponse<ProductResponse> addNewProduct(@RequestBody ProductRequest productRequest) {
        ProductResponse productResponse = productCassandraService.save(productRequest);
        return new DataResponse<>(productResponse, HttpStatus.OK, "Add new a product is successful!");
    }

    @GetMapping("")
    public DataResponse<List<ProductResponse>> getAllProduct(){
        return new DataResponse<>(productCassandraService.getAllProduct(), HttpStatus.OK, "Get list product is successful!");
    }
}
