package com.tma.demo.dtos.responses;

import lombok.*;
import org.springframework.data.cassandra.repository.AllowFiltering;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AccountResponse {
    private UUID id;
    private String username;
    private String phoneNumber;
    private boolean active;
}
