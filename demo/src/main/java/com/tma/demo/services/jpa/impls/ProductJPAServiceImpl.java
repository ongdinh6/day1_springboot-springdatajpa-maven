package com.tma.demo.services.jpa.impls;

import com.tma.demo.entities.jpa.ProductJPA;
import com.tma.demo.exceptions.InternalServerException;
import com.tma.demo.exceptions.NotFoundException;
import com.tma.demo.repositories.jpa.IProductJPARepository;
import com.tma.demo.services.jpa.IProductJPAService;
import com.tma.demo.utils.LogUtil;
import com.tma.demo.utils.UUIDHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductJPAServiceImpl implements IProductJPAService {
    LogUtil logUtil = LogUtil.getInstance();
    Logger logger = LoggerFactory.getLogger(ProductJPAServiceImpl.class);
    @Autowired
    IProductJPARepository productJPARepository;

    @Override
    public ProductJPA save(ProductJPA productJpa) {
        logger.debug("start-"+this.getClass()+"-save method");
        ProductJPA productToSave;
        if (null == (productToSave = productJPARepository.save(productJpa))) {
            logger.error(this.getClass().getSimpleName() + "-" + "server can not save product" + "-" + logger.getName());
            logUtil.setLogUtil("Server cannot save product error.", logger);
            logger.debug("end-"+this.getClass()+"-save method throw exception");
            throw new InternalServerException("Ops, server can not save product! Please try again later!");
        } else {
            logger.debug("end-"+this.getClass()+"-save method is successful");
            return productToSave;
        }
    }

    @Override
    public List<ProductJPA> getAllProduct() {
        return productJPARepository.findAll();
    }

    @Override
    public ProductJPA getById(String productId) {
        logger.debug("start - "+this.getClass()+" - getById() method");
        if (!productJPARepository.existsById(UUIDHelper.toUUID(productId))) {
            logger.error(this.getClass().getSimpleName() + "-" + "not found product with id: " + productId + "-" + logger.getName());
            logUtil.setLogUtil("Not found product with id " + productId, logger);
            logger.debug("end - "+this.getClass()+" - getById() method");
            throw new NotFoundException("Not found product with id: " + productId);
        } else {
            ProductJPA productJPA = productJPARepository.getById(UUID.fromString(productId));
            logger.debug("end - "+this.getClass()+" - getById() method");
            return productJPA;
        }
    }

}
