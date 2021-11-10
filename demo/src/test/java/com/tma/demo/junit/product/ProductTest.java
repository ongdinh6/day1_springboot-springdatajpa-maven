package com.tma.demo.junit.product;

import com.tma.demo.dtos.requests.ProductRequest;
import com.tma.demo.dtos.responses.ProductResponse;
import com.tma.demo.entities.cassandra.Product;
import com.tma.demo.repositories.cassandra.IProductCassandraRepository;
import com.tma.demo.services.cassandra.IProductCassandraService;
import com.tma.demo.services.cassandra.impls.ProductCassandraServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
@DataJpaTest// config với H2 Database, setting hibernate, spring jpa, datasource, entities scan, log.
public class ProductTest {

    //test repository co hoat dong khong
    @Autowired
    TestEntityManager entityManager;//không quan tâm csdl nào

//    @Autowired
//    IProductJPAService productJPAService;

    @TestConfiguration
    static class ProductCassandraServiceImplConfiguration {
        @Bean
        public IProductCassandraService impleService(){
            return new ProductCassandraServiceImpl();
        }
    }

    @Before
    public void setUp() throws Exception {
        ProductRequest product = new ProductRequest();
        product.setClazz("test-product-clazz-cassandra");
        product.setItem(20);
        product.setInventory("test-product-inventory-cassandra");
        Product p = product.toCassandraObject();
        Mockito.when(cassandraRepository.findByClazz(product.getClazz())).thenReturn(p);
    }

    @Autowired
    IProductCassandraService productCassandraService;

    @MockBean
    IProductCassandraRepository cassandraRepository;

    @Test
    public void testAddNewProduct_CassandraDataSource() {
//        ProductJPA productCassandra = product.toJpaObject();
//        entityManager.persist(productCassandra);
//        entityManager.flush();

        ProductRequest p = new ProductRequest();
        p.setItem(11);
        p.setClazz("cas-1");
        p.setInventory("inv-1");
        Product ps = productCassandraService.findByClazz("test-product-clazz-cassandra");
        assertNotNull(ps);

//        Product productSaved = productJPAService.save();
//        assertNotNull(productSaved);
    }

//    @Test
//    public void testGetProductByClazz(){
//        String clazz = "test-product-clazz-cassandra";
//        ProductJPA product = productJPAService.findByClazz(clazz);
//
//        assertNull(product);
//    }


    @Test
    public void testAuthentication() {
        String username = "anguyen";
        String password = "12345";



    }
}
