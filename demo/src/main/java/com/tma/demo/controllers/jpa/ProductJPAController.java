package com.tma.demo.controllers.jpa;

import com.tma.demo.dtos.DataResponse;
import com.tma.demo.dtos.requests.RequestSingleBodyType;
import com.tma.demo.dtos.responses.ProductResponse;
import com.tma.demo.entities.cassandra.Product;
import com.tma.demo.services.cassandra.IProductCassandraService;
import com.tma.demo.services.jpa.IProductJPAService;
import com.tma.demo.utils.UUIDHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductJPAController {
    @Autowired
    IProductJPAService productJPAService;
    @Autowired
    IProductCassandraService productCassandraService;

    @PostMapping("/new")
    public DataResponse<ProductResponse> addNewProduct(@RequestBody RequestSingleBodyType<String> productId) {
        Product productCassandra = productCassandraService.getById(UUIDHelper.toUUID(productId.getT()));
        ProductResponse productResponse = productJPAService.save(productCassandra.toProductJPA()).toProductResponse();
        return new DataResponse<>(productResponse, HttpStatus.OK, "Add new a product into postgresDB is successful!");
    }

    @GetMapping("")
    public DataResponse<List<ProductResponse>> getAllProduct(@RequestParam(required = false, defaultValue = "") String clazz) {
        //we need to convert list productJpa to list productResponse
        List<ProductResponse> products = new ArrayList<>();
        if(clazz.equalsIgnoreCase("")) {
            productJPAService.getAllProduct().forEach(product -> {
                products.add(product.toProductResponse());
            });
        }else{
            productJPAService.getAllProductByQueryDsl(clazz).forEach(product -> {
                products.add(product.toProductResponse());
            });
        }
        return new DataResponse<>(products, HttpStatus.OK, "Get list product from postgresDB is successful!");
    }

    @GetMapping("/{id}")
    public DataResponse<ProductResponse> getProductById(@PathVariable String id){
        return new DataResponse<>(productJPAService.getById(id).toProductResponse(), HttpStatus.OK, "Get product with id: "+id+" is successful!");
    }



}
