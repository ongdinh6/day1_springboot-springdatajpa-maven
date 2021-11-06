package com.tma.demo.controllers.elasticsearch;

import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Id;

@Document(indexName = "elasticsearch_data")
public class Customer {
    @Id
    private int id;
    private String name;
    private String address;

}
