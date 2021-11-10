package com.tma.demo.integration.controller_layer;

import com.tma.demo.configs.spring_security.JwtAuthenticationEntryPoint;
import com.tma.demo.configs.spring_security.JwtTokenUtil;
import com.tma.demo.controllers.jpa.ProductJPAController;
import com.tma.demo.dtos.DataResponse;
import com.tma.demo.dtos.responses.ProductResponse;
import com.tma.demo.entities.jpa.ProductJPA;
import com.tma.demo.repositories.jpa.IProductJPARepository;
import com.tma.demo.services.cassandra.IProductCassandraService;
import com.tma.demo.services.jpa.impls.JwtUserDetailsService;
import com.tma.demo.services.jpa.impls.ProductJPAServiceImpl;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Mock
    IProductJPARepository productJPARepository;

    @MockBean
    JwtUserDetailsService jwtUserDetailsService;

    @MockBean
    JwtTokenUtil jwtTokenUtil;

    @MockBean
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @MockBean
    IProductCassandraService productCassandraService;


    @WithMockUser(value = "anguyen")
    @Test
    void testGetAllProductAPI_shouldSucceedWithStatus200() {
        //step1: create mock data is a list productJpa
        List<ProductJPA> products = new ArrayList<>();
        products.add(new ProductJPA(UUID.randomUUID(), 20, "product-test-clazz-01", "product-test-inventory-01", new HashSet<>()));
        products.add(new ProductJPA(UUID.randomUUID(), 20, "product-test-clazz-02", "product-test-inventory-02", new HashSet<>()));
        products.add(new ProductJPA(UUID.randomUUID(), 20, "product-test-clazz-03", "product-test-inventory-03", new HashSet<>()));
        products.add(new ProductJPA(UUID.randomUUID(), 20, "product-test-clazz-04", "product-test-inventory-04", new HashSet<>()));

        //step2: define behavior for repository
        Mockito.when(productJPARepository.findAll()).thenReturn(products);

        //step3: call method getAllProduct service
        //productJPAService.getAllProduct();

        String url = "/api/v1/products";
//        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get(url)
//                .contentType(MediaType.APPLICATION_JSON)
//                .with(user("anguyen").password("12345")
//                        .authorities(new SimpleGrantedAuthority("admin")));;
        //we need to convert list productJpa to list productResponse
        List<ProductResponse> productResponses = new ArrayList<>();

        //actual result is 4
        productJPAService.getAllProduct().forEach(product -> {
            productResponses.add(product.toProductResponse());
        });
        DataResponse<List<ProductResponse>> dataResponse = new DataResponse<>(productResponses, HttpStatus.OK, "Get list product from postgresDB is successful!");
        try {
            ResultActions resultMatchers = mockMvc.perform(get(url).with(csrf())).andExpect(status().isOk()).andExpect(content().string(String.valueOf(dataResponse.getData().size())));
            //assertEquals(expect, actual), we want to have 4 elements in list
            assertEquals(resultMatchers, dataResponse.getData().size());

        } catch (Exception e) {
            e.printStackTrace();
        }
        //step4: ensure repository called least 1 times
        verify(productJPARepository).findAll();
    }
}

