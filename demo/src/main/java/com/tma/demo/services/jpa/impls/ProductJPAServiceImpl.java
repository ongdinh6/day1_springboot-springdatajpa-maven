package com.tma.demo.services.jpa.impls;

import com.tma.demo.dtos.responses.ProductResponse;
import com.tma.demo.entities.cassandra.Product;
import com.tma.demo.entities.jpa.ProductJPA;
import com.tma.demo.exceptions.InternalServerException;
import com.tma.demo.exceptions.NotFoundException;
import com.tma.demo.repositories.jpa.IProductJPARepository;
import com.tma.demo.services.jpa.IProductJPAService;
import com.tma.demo.utils.DateTimeUtil;
import com.tma.demo.utils.LogUtil;
import com.tma.demo.utils.UUIDHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductJPAServiceImpl implements IProductJPAService {
    LogUtil logUtil = LogUtil.getInstance();
    Logger logger = LoggerFactory.getLogger(ProductJPAServiceImpl.class);
    @Autowired
    IProductJPARepository productJPARepository;

    @Override
    public ProductResponse save(Product productCassandra) {
        ProductJPA productToSave;
        if (null == (productToSave = productJPARepository.save(productCassandra.toProductJPA()))) {
            logger.error(this.getClass().getSimpleName() + "-" + "server can not save product" + "-" + logger.getName());
            logUtil.setLogUtil("Server cannot save product error.", logger);
            throw new InternalServerException("Ops, server can not save product! Please try again later!");
        } else {
            return productToSave.toProductResponse();
        }
    }

    @Override
    public List<ProductResponse> getAllProduct() {
        List<ProductResponse> products = new ArrayList<>();
        productJPARepository.findAll().forEach(product -> {
            products.add(product.toProductResponse());
        });
        return products;
    }

    @Override
    public ProductResponse getById(String productId) {
        logger.debug("start - "+this.getClass()+" - getById() method");
        if (!productJPARepository.existsById(UUIDHelper.toUUID(productId))) {
            logger.error(this.getClass().getSimpleName() + "-" + "not found product with id: " + productId + "-" + logger.getName());
            logUtil.setLogUtil("Not found product with id " + productId, logger);
            logger.debug("end - "+this.getClass()+" - getById() method");
            throw new NotFoundException("Not found product with id: " + productId);
        } else {
            ProductJPA productJPA = productJPARepository.getById(UUID.fromString(productId));
            logger.debug("end - "+this.getClass()+" - getById() method");
            return productJPA.toProductResponse();
        }
    }

}
