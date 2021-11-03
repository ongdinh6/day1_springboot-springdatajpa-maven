package com.tma.demo.entities.jpa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
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
    private Set<ProductJPA> products;

    @OneToMany(
            mappedBy = "saleJPA",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<TimeJPA> times;

    @OneToMany(
            mappedBy = "saleJPA",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<LocationJPA> locationJPAS;

    private BigDecimal dollars;
}
