package com.tma.demo.dtos.responses;

import com.tma.demo.entities.jpa.SaleJPA;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductResponse {
    private UUID id;
    private int item;
    private String clazz;
    private String investory;
}
