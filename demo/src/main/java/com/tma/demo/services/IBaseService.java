package com.tma.demo.services;

import java.util.List;

public interface IBaseService<T> {
    T save(T t);
    List<T> getAll();
    T getById(String tId);
}
