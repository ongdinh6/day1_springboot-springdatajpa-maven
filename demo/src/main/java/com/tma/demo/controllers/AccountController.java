package com.tma.demo.controllers;

import com.tma.demo.dtos.AccountRequest;
import com.tma.demo.dtos.DataResponse;
import com.tma.demo.entities.Account;
import com.tma.demo.services.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {
    @Autowired
    IAccountService accountService;

    @GetMapping("")
    //this request mapping url is: http://localhost:8080/api/v1/accounts
    public List<Account> getAllCounts(){
        return accountService.getAll();
    }

    @PostMapping("/new")
    //this request mapping url is: http:/localhost:8080/api/v1/accounts/new
    public ResponseEntity<DataResponse> addAnNewAccount(@RequestBody AccountRequest accountRequest){
        Account accountToSave = accountRequest.toEntity();//convert transient object to persistent object
        System.out.println("- INSERT: "+accountRequest.toString());

        //saving this account into table
        if(accountService.save(accountToSave)) {
            return new ResponseEntity<DataResponse>(new DataResponse<AccountRequest>(accountRequest, HttpStatus.OK, "An account was inserted into the table successfully!"), HttpStatus.OK);
        }else {
            return null;//just return for test
        }
    }
}
