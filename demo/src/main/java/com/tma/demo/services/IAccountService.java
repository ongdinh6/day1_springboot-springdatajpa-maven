package com.tma.demo.services;

import com.tma.demo.entities.Account;

import java.util.List;

public interface IAccountService {
    List<Account> getAll();
    boolean save(Account account);
}
