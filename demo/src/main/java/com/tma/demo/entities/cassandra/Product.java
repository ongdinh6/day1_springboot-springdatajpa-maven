package com.tma.demo.entities.cassandra;


import com.tma.demo.dtos.responses.ProductResponse;
import com.tma.demo.entities.jpa.ProductJPA;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import javax.persistence.Entity;
import java.awt.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("product")
public class Product {
    @PrimaryKey
    @CassandraType(type = CassandraType.Name.UUID)
    @Column("product_id")
    private UUID productId;
    private int item;
    private String clazz;
    private String investory;

    public ProductResponse toProductResponse(){
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(productId);
        productResponse.setItem(item);
        productResponse.setClazz(clazz);
        productResponse.setInvestory(investory);

        return productResponse;
    }

    public ProductJPA toProductJPA(){
        ProductJPA productJPA = new ProductJPA();
        productJPA.setProductId(productId);
        productJPA.setItem(item);
        productJPA.setClazz(clazz);
        productJPA.setInvestory(investory);

        return productJPA;
    }
}
