package com.tma.demo.entities.jpa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "time")
@Entity
public class TimeJPA {
    @Id
    @Column(name = "time_id")
    private UUID timeId;
    private int month;
    private int quarter;
    private int year;
    @ManyToOne(fetch = FetchType.LAZY)
    private SaleJPA saleJPA;
}
