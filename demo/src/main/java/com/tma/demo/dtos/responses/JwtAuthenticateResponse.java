package com.tma.demo.dtos.responses;

import com.tma.demo.entities.jpa.UserJPA;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthenticateResponse {
    private String jwt;
    private AuthorizationResponse authorization;
}
