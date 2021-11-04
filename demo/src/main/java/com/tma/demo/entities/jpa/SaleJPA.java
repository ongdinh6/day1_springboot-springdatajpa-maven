package com.tma.demo.entities.jpa;

import com.fasterxml.jackson.databind.deser.BuilderBasedDeserializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sales")
@Entity
public class SaleJPA {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long saleId;
    @OneToMany(
            mappedBy = "saleJPA",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<ProductJPA> products = new HashSet<>();

    @OneToMany(
            mappedBy = "saleJPA",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<TimeJPA> times = new HashSet<>();

    @OneToMany(
            mappedBy = "saleJPA",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<LocationJPA> locationJPAS = new HashSet<>();

    private BigDecimal dollars;
}
