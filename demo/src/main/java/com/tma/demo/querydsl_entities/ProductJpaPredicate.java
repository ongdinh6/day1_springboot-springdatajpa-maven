package com.tma.demo.querydsl_entities;

import com.querydsl.core.types.Predicate;
import com.tma.demo.entities.jpa.QProductJPA;

public class ProductJpaPredicate {
    private static QProductJPA qProductJpa = QProductJPA.productJPA;

    public static Predicate getAllByClazzContains(String clazz){
        System.out.println("DO GET BY QUERY DSL");
        return qProductJpa.clazz.containsIgnoreCase(clazz);
    }
}
