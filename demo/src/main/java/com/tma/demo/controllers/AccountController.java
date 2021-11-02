package com.tma.demo.controllers;

import com.tma.demo.dtos.DataResponse;
import com.tma.demo.dtos.requests.AccountRequest;
import com.tma.demo.dtos.responses.AccountResponse;
import com.tma.demo.services.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {
    @Autowired
    IAccountService accountService;

    @GetMapping("")
    //this request mapping url is: http://localhost:8080/api/v1/accounts
    public ResponseEntity<DataResponse<List<AccountResponse>>> getAllCounts(){
        ResponseEntity<DataResponse<List<AccountResponse>>> response = new ResponseEntity<DataResponse<List<AccountResponse>>>(
                                                                    new DataResponse<List<AccountResponse>>(accountService.getAll(), HttpStatus.OK, "Get list accounts successfully!"), HttpStatus.OK);
        return response;
    }

    @PostMapping("/new")
    //this request mapping url is: http:/localhost:8080/api/v1/accounts/new
    public ResponseEntity<DataResponse> addAnNewAccount(@RequestBody AccountRequest accountRequest){
        //saving this account into table
        if(accountService.save(accountRequest)) {
            return new ResponseEntity<DataResponse>(new DataResponse<AccountRequest>(accountRequest, HttpStatus.OK, "An account was inserted into the table successfully!"), HttpStatus.OK);
        }else {
            return null;//just return for test
        }
    }

}
