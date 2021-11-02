package com.tma.demo.dtos.requests;

import com.tma.demo.entities.Account;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AccountRequest {
    private String username;
    private String password;
    private String confirmPassword;
    private String phoneNumber;

    public Account toEntity() {
        return new Account(UUID.randomUUID(), username, password, phoneNumber, false);
    }
}
