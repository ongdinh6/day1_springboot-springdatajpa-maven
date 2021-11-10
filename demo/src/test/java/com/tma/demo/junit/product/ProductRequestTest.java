package com.tma.demo.junit.product;

import com.tma.demo.dtos.requests.ProductRequest;
import com.tma.demo.entities.cassandra.Product;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.instanceOf;

public class ProductRequestTest {
    private ProductRequest productRequest;

    public ProductRequestTest(){
        this.productRequest = new ProductRequest();
    }

    @BeforeEach
    void setUp() {
        productRequest.setItem(20);
        productRequest.setClazz("product-request-1");
        productRequest.setInventory("product-inventory-1");
    }

    @Test
    void test_toCassandraObject_method_returnProduct() {
        Assert.assertThat(productRequest.toCassandraObject(), instanceOf(Product.class));
    }
}
