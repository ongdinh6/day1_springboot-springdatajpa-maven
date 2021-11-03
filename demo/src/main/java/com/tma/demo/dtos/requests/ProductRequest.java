package com.tma.demo.dtos.requests;

import com.tma.demo.entities.cassandra.Product;
import com.tma.demo.entities.jpa.ProductJPA;
import liquibase.pro.packaged.T;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductRequest {
    private int item;
    private String clazz;
    private String investory;

    public Product toCassandraObject() {
        Product product = new Product();
        product.setProductId(UUID.randomUUID());
        product.setItem(item);
        product.setClazz(clazz);
        product.setInvestory(investory);
        return product;
    }
    public ProductJPA toJpaObject() {
        ProductJPA product = new ProductJPA();
        product.setProductId(UUID.randomUUID());
        product.setItem(item);
        product.setClazz(clazz);
        product.setInvestory(investory);
        return product;
    }
}
