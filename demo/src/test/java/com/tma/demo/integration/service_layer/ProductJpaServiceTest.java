package com.tma.demo.integration.service_layer;

import com.tma.demo.dtos.responses.ProductResponse;
import com.tma.demo.entities.cassandra.Product;
import com.tma.demo.entities.jpa.ProductJPA;
import com.tma.demo.exceptions.BadRequestException;
import com.tma.demo.exceptions.InternalServerException;
import com.tma.demo.exceptions.NotFoundException;
import com.tma.demo.repositories.jpa.IProductJPARepository;
import com.tma.demo.services.cassandra.IProductCassandraService;
import com.tma.demo.services.cassandra.impls.ProductCassandraServiceImpl;
import com.tma.demo.services.jpa.IProductJPAService;
import com.tma.demo.services.jpa.impls.ProductJPAServiceImpl;
import com.tma.demo.utils.UUIDHelper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/*
* Test service layer, we will don't care about controller and repository layer
* using  @MockBean to create a mock repository*/
//code reference by Code Java youtube chanel
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class ProductJpaServiceTest {

    @MockBean
    IProductJPARepository productJpaRepository;

    @InjectMocks
    ProductJPAServiceImpl productJpaService;

    /* all method of productJPAService class
     * ProductResponse save(ProductJPA productJpa);
      List<Product> getAllProduct();
      Product getById(String productId);
    * */

    /*
    * a test method usually through 4 step presented in below code */

    @Test
    void testSaveProduct_successful() {
        //step1: create mock data
        ProductJPA productJpa = new ProductJPA();
        productJpa.setProductId(UUID.randomUUID());
        productJpa.setItem(22);
        productJpa.setClazz("test-product-01");
        productJpa.setInventory("test-inventory-product-01");

       /* step2: define behavior of repository
        if we call save method at service class that always return this productJpa*/
        Mockito.when(productJpaRepository.save(productJpa)).thenReturn(productJpa);

        //step3: call service method
        productJpaService.save(productJpa);

        //step4: assert the result
        assertNotNull(productJpa);
    }

    /*
    * test save method always throwing exception InternalServerException
    * */
    @Test
    void testSaveProduct_throwInternalServerException() {
        //step1: create mock data
        ProductJPA productJpa = new ProductJPA();
        //productJpa cannot save without id attribute
        productJpa.setItem(22);
        productJpa.setClazz("test-product-02");
        productJpa.setInventory("test-inventory-product-02");

        //step2: define behavior of repository
        //if we call save method at service class that always return this productJpa
        Mockito.when(productJpaRepository.save(productJpa)).thenThrow(new InternalServerException("Ops, server can not save product! Please try again later!"));

        //step3+4: save and assert the result, we expect to this method will be throw an InternalServerException exception
        assertThatThrownBy(() -> productJpaService.save(productJpa))
                .isInstanceOf(InternalServerException.class);
    }

    /*this method will be return a list productJPA*/
    @Test
    void testGetAllProduct_successful() {
        //step1: create a mock data is a list product
        List<ProductJPA> products = new ArrayList<>();
        products.add(new ProductJPA(UUID.randomUUID(), 20, "product-test-clazz-01", "product-test-inventory-01", new HashSet<>()));
        products.add(new ProductJPA(UUID.randomUUID(), 20, "product-test-clazz-02", "product-test-inventory-02", new HashSet<>()));
        products.add(new ProductJPA(UUID.randomUUID(), 20, "product-test-clazz-03", "product-test-inventory-03", new HashSet<>()));
        products.add(new ProductJPA(UUID.randomUUID(), 20, "product-test-clazz-04", "product-test-inventory-04", new HashSet<>()));

        //step2: define behavior for repository
        /*if we call findAll method in repository class that return a list at step 1*/
        Mockito.when(productJpaRepository.findAll()).thenReturn(products);

        //step3: call service method
        int size = productJpaService.getAllProduct().size();

        //step4: assert result
        /* we are expecting to this method will be return a list product have 4 elements */
        Assert.assertEquals(products.size(), size);
    }

    /*test method getById with 3 cases are: exist, not-exist throw NotFoundException, id is invalid throw BadRequestException */
    @Test
    void testGetById_shouldBeReturnAProductHasTheSameId() {
        //step1: create a product has id is '8885c898-4d5f-434d-9bc6-1bfbfeb0c209'
        UUID id = UUID.randomUUID();
        ProductJPA productJpa = new ProductJPA();
        productJpa.setProductId(id);
        productJpa.setItem(20);
        productJpa.setClazz("test-getbyid-product-01");
        productJpa.setInventory("test-inventory-getbyid-product-01");

        //step2: mock data for repository
        /*when we call existsById method in repository class that will be return true*/
        Mockito.when(productJpaRepository.existsById(productJpa.getProductId()))
                .thenReturn(true);
        //because my code is check existed for throw not found exception so we have this code
        Mockito.when(productJpaRepository.getById(productJpa.getProductId()))
                .thenReturn(productJpa);

        //step3: call service method
        ProductJPA productResult = productJpaService.getById(String.valueOf(id));

        //step4: assert with null
        Assert.assertNotNull(productResult);
    }

    @Test
    void testGetById_throwNotFoundException() {
        //step1: Product not found with id '8885c898-4d5f-434d-9bc6-1bfbfeb0c209'
        String id = "8885c898-4d5f-434d-9bc6-1bfbfeb0c209";

        //step3+4: call service method and assert with NotFoundException class
        assertThatThrownBy(() ->  productJpaService.getById(id)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void testGetById_throwBadRequestException() {
        //step1: UUIDHelper will be throw a BadRequestException
        String id = "blablabla";

        //step3+4: call UUIDHelper method and assert with NotFoundException class
        assertThatThrownBy(() ->  UUIDHelper.toUUID(id)).isInstanceOf(BadRequestException.class);
    }



}
