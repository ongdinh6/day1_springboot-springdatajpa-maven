package com.tma.demo.repositories.jpa;

import com.tma.demo.entities.jpa.ProductJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IProductJPARepository extends JpaRepository<ProductJPA, UUID>, QuerydslPredicateExecutor<ProductJPA> {
}
