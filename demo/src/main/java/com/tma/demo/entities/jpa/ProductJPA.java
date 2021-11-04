package com.tma.demo.entities.jpa;


import com.tma.demo.dtos.responses.ProductResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class ProductJPA {
    @Id
    @Column(name = "product_id")
    private UUID productId;
    private int item;
    private String clazz;
    private String inventory;
    @ManyToOne(fetch = FetchType.LAZY)
    private SaleJPA saleJPA;

    public ProductResponse toProductResponse(){
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(productId);
        productResponse.setItem(item);
        productResponse.setClazz(clazz);
        productResponse.setInventory(inventory);

        return productResponse;
    }
}
