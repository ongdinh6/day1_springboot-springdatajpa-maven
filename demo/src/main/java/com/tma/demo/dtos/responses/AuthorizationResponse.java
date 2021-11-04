package com.tma.demo.dtos.responses;

import com.tma.demo.entities.jpa.RoleJPA;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorizationResponse {
    private UUID userId;
    private String username;
    private Set<RoleJPA> roles;
}
