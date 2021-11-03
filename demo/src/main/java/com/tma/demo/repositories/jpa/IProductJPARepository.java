package com.tma.demo.repositories.jpa;

import com.tma.demo.entities.jpa.ProductJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IProductJPARepository extends JpaRepository<ProductJPA, UUID> {
}
