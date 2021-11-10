package com.tma.demo.entities.elasticsearch;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.UUID;

@Document(indexName = "product")
@Setter
@Getter
public class ProductElasticsearch {
    @Id
    private UUID productId;
    private int item;
    private String clazz;
    private String inventory;
}
