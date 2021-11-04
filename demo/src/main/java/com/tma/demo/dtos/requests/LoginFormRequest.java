package com.tma.demo.dtos.requests;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginFormRequest {
    private String username;
    private String password;
}
