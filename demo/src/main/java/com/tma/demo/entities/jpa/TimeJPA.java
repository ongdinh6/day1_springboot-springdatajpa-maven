package com.tma.demo.entities.jpa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "times")
@Entity
public class TimeJPA {
    @Id
    @Type(type = "pg-uuid")
    @Column(name = "time_id", columnDefinition = "uuid")
    private UUID timeId;
    private int month;
    private int quarter;
    private int year;
    @OneToMany(
            mappedBy = "timeJPA",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<SaleJPA> saleJPAS = new HashSet<>();
}
