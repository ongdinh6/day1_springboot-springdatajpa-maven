package com.tma.demo.integration.controller_layer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.tma.demo.configs.spring_security.JwtAuthenticationEntryPoint;
import com.tma.demo.configs.spring_security.JwtRequestFilter;
import com.tma.demo.configs.spring_security.JwtTokenUtil;
import com.tma.demo.controllers.jpa.ProductJPAController;
import com.tma.demo.dtos.DataResponse;
import com.tma.demo.dtos.responses.ProductResponse;
import com.tma.demo.entities.cassandra.Product;
import com.tma.demo.entities.jpa.ProductJPA;
import com.tma.demo.repositories.cassandra.IProductCassandraRepository;
import com.tma.demo.repositories.jpa.IProductJPARepository;
import com.tma.demo.services.cassandra.IProductCassandraService;
import com.tma.demo.services.jpa.IProductJPAService;
import com.tma.demo.services.jpa.impls.JwtUserDetailsService;
import com.tma.demo.services.jpa.impls.ProductJPAServiceImpl;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductJPAController.class)
public class ProductJPAControllerTest {
    /*ProductJPAController Class has:
     * 1. getAllProduct
     * 2. addNewProduct
     * 3. getProductById
     * */
    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProductJPAServiceImpl productJPAService;

    @InjectMocks
    ProductJPAController productJPAController;

    @MockBean
    IProductJPARepository productJPARepository;

    @MockBean
    JwtUserDetailsService jwtUserDetailsService;

    @MockBean
    JwtTokenUtil jwtTokenUtil;

    @MockBean
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @MockBean
    IProductCassandraService productCassandraService;
    @MockBean
    IProductCassandraRepository productCassandraRepository;

    @Test
    void testGetAllProductAPI_shouldSucceedWithStatus200() {
        //step1: create mock data is a list productJpa
        List<ProductJPA> products = new ArrayList<>();
        products.add(new ProductJPA(UUID.randomUUID(), 20, "product-test-clazz-01", "product-test-inventory-01", new HashSet<>()));
        products.add(new ProductJPA(UUID.randomUUID(), 20, "product-test-clazz-02", "product-test-inventory-02", new HashSet<>()));
        products.add(new ProductJPA(UUID.randomUUID(), 20, "product-test-clazz-03", "product-test-inventory-03", new HashSet<>()));
        products.add(new ProductJPA(UUID.randomUUID(), 20, "product-test-clazz-04", "product-test-inventory-04", new HashSet<>()));

        //step2: define behavior for repository (when getAllProduct method in productJPAService be called, we need to return list productJpa like step 1)
        Mockito.when(productJPAService.getAllProduct()).thenReturn(products);

        //step3: mock perform url
        String url = "/api/v1/products";
        try {
            MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get(url)
                    .contentType(MediaType.APPLICATION_JSON);
           /* from json return, we need to get data array and do
            assertEquals(expect, actual), we want to have 4 elements in list*/
            mockMvc.perform(mockRequest).andExpect(status().isOk()).andExpect(jsonPath("$.data", hasSize(products.size())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //step4: ensure service called least 1 times
        verify(productJPAService).getAllProduct();
    }

    @Test
    void testAddNewProduct_shouldSucceedWithStatus200() {
        //step1: create mock data productJpa
        Product product = new Product(UUID.randomUUID(), 20, "product-test-clazz-01", "product-test-inventory-01");
        ProductJPA productJpa = new ProductJPA(product.getProductId(), 20, "product-test-clazz-01", "product-test-inventory-01", new HashSet<>());

        //step2: define behavior service class
        //we need to when we call productJpaService that return productJpa mock data
        Mockito.when(productCassandraRepository.getByProductId(product.getProductId())).thenReturn(product);
        Mockito.when(productCassandraService.getById(productJpa.getProductId())).thenReturn(product);
        Mockito.when(productJPAService.save(any())).thenReturn(productJpa);


        //step3+4: call method mockMvc perform and assert result
        String url = "/api/v1/products/new";
        String productJpaJson = "{" +
                            "\"t\":"+"\""+product.getProductId()+"\""+ "}";
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post(url).content(productJpaJson)
                .contentType(MediaType.APPLICATION_JSON);
        try {
            //we expect the result for perform has 1 elements return is productJpa at step1
            mockMvc.perform(mockRequest).andExpect(status().isOk())
                    .andExpect(jsonPath("$.statusString", is("200 OK")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testAddNewProduct_throwInternalServerExceptionWithStatus500() {

    }
}

