package com.tma.demo.services;

import com.tma.demo.dtos.requests.AccountRequest;
import com.tma.demo.dtos.responses.AccountResponse;
import com.tma.demo.entities.Account;

import java.util.List;

public interface IAccountService<T> {
    List<AccountResponse> getAll();
    boolean save(AccountRequest account);
}
