package com.tma.demo.services.impls;

import com.tma.demo.entities.Account;
import com.tma.demo.repositories.IAccountRepository;
import com.tma.demo.services.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountServiceImpl implements IAccountService {
    @Autowired
    IAccountRepository accountRepo;

    @Override
    //this method using to get all account in cassandra database
    public List<Account> getAll() {
        List<Account> accounts = new ArrayList();
        accountRepo.findAll().forEach(accounts::add);
        return accounts;
    }

    @Override
    //to save an account into table 'accounts'
    public boolean save(Account account) {
        return accountRepo.save(account) != null ? true : false;
    }
}
