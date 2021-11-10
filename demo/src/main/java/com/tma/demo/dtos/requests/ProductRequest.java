package com.tma.demo.dtos.requests;

import com.tma.demo.entities.cassandra.Product;
import com.tma.demo.entities.jpa.ProductJPA;
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
    private String inventory;

    public Product toCassandraObject() {
        Product product = new Product();
        product.setProductId(UUID.randomUUID());
        product.setItem(item);
        product.setClazz(clazz);
        product.setInventory(inventory);
        return product;
    }
    public ProductJPA toJpaObject() {
        ProductJPA product = new ProductJPA();
        product.setProductId(UUID.randomUUID());
        product.setItem(item);
        product.setClazz(clazz);
        product.setInventory(inventory);
        return product;
    }
}
