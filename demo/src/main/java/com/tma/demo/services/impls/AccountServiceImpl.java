package com.tma.demo.services.impls;

import com.tma.demo.dtos.requests.AccountRequest;
import com.tma.demo.dtos.responses.AccountResponse;
import com.tma.demo.entities.Account;
import com.tma.demo.repositories.IAccountCassandraRepository;
import com.tma.demo.services.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountServiceImpl implements IAccountService {
    @Autowired
    IAccountCassandraRepository accountRepo;

    @Override
    //this method using to get all account in cassandra database
    public List<AccountResponse> getAll() {
        List<AccountResponse> accounts = new ArrayList();
        accountRepo.findAll().forEach(x -> {
            accounts.add(x.toAccountResponse());
        });
        return accounts;
    }

    @Override
    //to save an account into table 'accounts'
    public boolean save(AccountRequest account) {
        return accountRepo.save(account.toEntity()) != null ? true : false;
    }
}
