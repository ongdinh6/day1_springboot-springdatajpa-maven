package com.tma.demo.entities.jpa;


import com.tma.demo.dtos.responses.ProductResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="product")
public class ProductJPA {
    @Id
    @Type(type = "pg-uuid")
    @Column(name = "product_id", columnDefinition = "uuid")
    private UUID productId;
    private int item;
    private String clazz;
    private String inventory;
    @OneToMany(
            mappedBy = "productJPA",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<SaleJPA> saleJPAS = new HashSet<>();

    public ProductResponse toProductResponse(){
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(productId);
        productResponse.setItem(item);
        productResponse.setClazz(clazz);
        productResponse.setInventory(inventory);

        return productResponse;
    }
}
